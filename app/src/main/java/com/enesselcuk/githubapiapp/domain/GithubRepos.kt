package com.enesselcuk.githubapiapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.enesselcuk.githubapiapp.data.remote.model.Item
import com.enesselcuk.githubapiapp.data.remote.model.SearchResponse
import com.enesselcuk.githubapiapp.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface GithubRepos {
      fun getSearch(name:String): Flow<PagingData<Item>>
}