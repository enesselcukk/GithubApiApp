package com.enesselcuk.githubapiapp.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item


object SearchDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem
}