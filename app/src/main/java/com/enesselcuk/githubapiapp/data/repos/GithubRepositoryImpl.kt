package com.enesselcuk.githubapiapp.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.enesselcuk.githubapiapp.data.local.GithubDataBase
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.paginDataSource.RemoteMediatorItem
import com.enesselcuk.githubapiapp.data.remote.model.detailResponse.DetailResponse
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.data.remote.service.GithubService
import com.enesselcuk.githubapiapp.domain.GithubRepos
import com.enesselcuk.githubapiapp.util.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubService,
    private val io: CoroutineDispatcher = Dispatchers.IO,
    private val database: GithubDataBase
) : GithubRepos {
    override fun getSearch(name: String): Flow<PagingData<Item>> {
        val dbQuery = "%${name.replace('%', '%')}%"
        val daoFactory = { database.githubDao().searchByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = RemoteMediatorItem(api, database, name),
            pagingSourceFactory = daoFactory
        ).flow

    }

    override suspend fun getDetail(name: String): Flow<NetworkResult<List<DetailResponse>>> =
        flow {
            emit(NetworkResult.Loading())

            val detailInfo = database.detailDao().searchByName(name).map {
                it.toDetailResponse()
            }
            emit(NetworkResult.Loading(data = detailInfo))
            try {
                val remoteDetail = api.getDetail(name)
                database.detailDao().deleteDetail(remoteDetail.id)

                database.detailDao().insertDetail(remoteDetail.toDetailMap())
            } catch (ex: Exception) {
                emit(NetworkResult.Error(ex.message))
            } catch (ex: IOException) {
                emit(NetworkResult.Error(ex.message))
            }

            val newDetailInfo = database.detailDao().searchByName(name).map {
                it.toDetailResponse()
            }
            emit(NetworkResult.Success(newDetailInfo))
        }.flowOn(io)


    override fun getAllGithub(): Flow<NetworkResult<List<Item>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val data = database.githubDao().allGithub()
            emit(NetworkResult.Success(data))
        } catch (ex: Exception) {
            emit(NetworkResult.Error(ex.message))
        } catch (ex: HttpException) {
            emit(NetworkResult.Error(ex.message))
        }
    }.flowOn(io)

    override suspend fun setGit(item: Item) {
        database.githubDao().insertGithub(item)
    }

    override suspend fun insertFav(favoriteEntity: FavoriteEntity) {
        database.favoriteDao().insertFav(favoriteEntity)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 1
    }
}