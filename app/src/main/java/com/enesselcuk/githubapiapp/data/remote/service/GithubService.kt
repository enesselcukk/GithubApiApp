package com.enesselcuk.githubapiapp.data.remote.service

import com.enesselcuk.githubapiapp.data.remote.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.github.com/search/users?q=enesselcuk
interface GithubService {

    @GET("search/users?")
    suspend fun getSearch(
        @Query("q") q: String,
        @Query("page") page: Int
    ): SearchResponse
}