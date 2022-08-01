package com.enesselcuk.githubapiapp.data.remote.model.searchModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(
    val incomplete_results: Boolean? = null,
    val items: List<Item>? = null,
    val total_count: Int? = null,
) : Parcelable