package com.enesselcuk.githubapiapp.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.enesselcuk.githubapiapp.data.remote.model.Item
import com.enesselcuk.githubapiapp.databinding.ItemSearchBinding


class SearchVHolder(private val binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        searchItem: Item,
    ) {
        binding.setData = searchItem

    }
}