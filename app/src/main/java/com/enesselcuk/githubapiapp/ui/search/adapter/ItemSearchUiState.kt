package com.enesselcuk.githubapiapp.ui.search.adapter

import com.enesselcuk.githubapiapp.data.remote.model.Item

data class ItemSearchUiState(
    private val item: Item? = null
) {
    fun getItem() = item
    fun getName() = item?.login
    fun getAvatar() = item?.avatar_url
    fun getId() = item?.id

}