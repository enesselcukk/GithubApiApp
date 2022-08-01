package com.enesselcuk.githubapiapp.ui.list


import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item


data class ListUiState(
    val isLoading:Boolean = true,
    val item: List<Item>? = emptyList(),
    val isError:String? = null
)


