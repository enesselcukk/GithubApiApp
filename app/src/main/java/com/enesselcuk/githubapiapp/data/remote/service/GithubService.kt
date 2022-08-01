package com.enesselcuk.githubapiapp.data.remote.service

import com.enesselcuk.githubapiapp.data.remote.model.detailResponse.DetailResponse
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users?")
    suspend fun getSearch(
        @Query("q") q: String,
        @Query("page") page: Int
    ): SearchResponse

    @GET("users/{login}")
    suspend fun getDetail(
        @Path("login") login: String
    ): DetailResponse


}