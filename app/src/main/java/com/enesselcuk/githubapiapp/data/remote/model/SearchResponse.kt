package com.enesselcuk.githubapiapp.data.remote.model

import com.google.gson.annotations.SerializedName


data class SearchResponse(
    val incomplete_results: Boolean?=null,
    val items: List<Item>?=null,
    val total_count: Int?=null,
)