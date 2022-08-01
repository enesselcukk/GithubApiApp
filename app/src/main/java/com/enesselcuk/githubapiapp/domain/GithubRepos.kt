package com.enesselcuk.githubapiapp.domain

import androidx.paging.PagingData
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.remote.model.detailResponse.DetailResponse
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface GithubRepos {
    fun getSearch(name: String): Flow<PagingData<Item>>
    suspend fun getDetail(name: String): Flow<NetworkResult<List<DetailResponse>>>
    fun getAllGithub(): Flow<NetworkResult<List<Item>>>
    suspend fun setGit(item: Item)
    suspend fun insertFav(favoriteEntity: FavoriteEntity)



}