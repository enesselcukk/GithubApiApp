package com.enesselcuk.githubapiapp.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.enesselcuk.githubapiapp.data.paginDataSource.UserPagingDataSource
import com.enesselcuk.githubapiapp.data.remote.model.Item
import com.enesselcuk.githubapiapp.data.remote.model.SearchResponse
import com.enesselcuk.githubapiapp.data.remote.service.GithubService
import com.enesselcuk.githubapiapp.domain.GithubRepos
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubService,
    private val io: CoroutineDispatcher = Dispatchers.IO
) : GithubRepos {
    override fun getSearch(name: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 20,
                enablePlaceholders = false

            ),
            pagingSourceFactory = { UserPagingDataSource(api, name) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 1
    }

}