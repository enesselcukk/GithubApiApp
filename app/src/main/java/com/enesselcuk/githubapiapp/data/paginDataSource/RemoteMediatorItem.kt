package com.enesselcuk.githubapiapp.data.paginDataSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.enesselcuk.githubapiapp.data.local.GithubDataBase
import com.enesselcuk.githubapiapp.data.local.item.pagingLocal.RemoteKeysEntity
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.data.remote.service.GithubService
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class RemoteMediatorItem(
    private val service: GithubService,
    private val database: GithubDataBase,
    private val query: String,
) : RemoteMediator<Int, Item>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Item>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey

                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                nextKey
            }
        }

        try {
            val apiResponse = service.getSearch(query, page)

            val repos = apiResponse.items
            val endOfPaginationReached = repos?.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.githubDao().clearRepos()
                }

                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page.minus(1)
                val nextKey = if ((endOfPaginationReached == true)) null else page.plus(1)

                val keys = repos?.map {
                    RemoteKeysEntity(
                        repoId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.remoteKeysDao().insertAll(keys)
                repos?.map {
                    database.githubDao().insertGithub(it)
                }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached!!)


        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Item>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.remoteKeysDao().remoteKeysRepoId(it.id!!.toLong())
            }

    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Item>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.remoteKeysDao().remoteKeysRepoId(it.id!!.toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Item>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao().remoteKeysRepoId(repoId.toLong())
            }
        }
    }

    companion object {
        const val GITHUB_STARTING_PAGE_INDEX = 1
    }

}