package com.enesselcuk.githubapiapp.data.paginDataSource


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.enesselcuk.githubapiapp.data.remote.model.Item
import com.enesselcuk.githubapiapp.data.remote.service.GithubService
import retrofit2.HttpException
import java.io.IOException

class UserPagingDataSource(
    private val service: GithubService,
    private val query: String,
) : PagingSource<Int, Item>() {
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.getSearch(query, page)
            LoadResult.Page(
                data = response.items.orEmpty(),
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.items!!.isEmpty()) null else page.plus(1)
            )

        } catch (ex: IOException) {
            return LoadResult.Error(ex)

        } catch (ex: HttpException) {
            return LoadResult.Error(ex)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}