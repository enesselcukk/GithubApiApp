package com.enesselcuk.githubapiapp.ui.detail

import com.enesselcuk.githubapiapp.data.remote.model.detailResponse.DetailResponse


data class DetailUiState(
    val isLoading: Boolean? = null,
    val detailResponse: List<DetailResponse>? = emptyList(),

)
