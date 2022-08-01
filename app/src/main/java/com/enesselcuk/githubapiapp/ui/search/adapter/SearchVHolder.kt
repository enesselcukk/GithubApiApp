package com.enesselcuk.githubapiapp.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.databinding.ItemSearchBinding


class SearchVHolder(private val binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        searchItem: Item,
        onClick: (Item) -> Unit,
    ) {
        binding.setData = searchItem
        binding.root.setOnClickListener {
            onClick.invoke(searchItem)
        }
    }
}