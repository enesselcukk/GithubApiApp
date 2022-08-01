package com.enesselcuk.githubapiapp.ui.list.listAdapter

import androidx.recyclerview.widget.RecyclerView
import com.enesselcuk.githubapiapp.R
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.databinding.ItemListBinding


class ListeningVHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        searchItem: Item,
        onClick: (Item) -> Unit,
        onGitItemClick: (Item) -> Unit,
        onFavoriteClick: (FavoriteEntity) -> Unit,


        ) {
        binding.setData = searchItem
        binding.root.setOnClickListener {
            onClick.invoke(searchItem)
        }



        if (searchItem.liked) {
            binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_dark)

        } else {
            binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)
        }

        binding.imageFavorite.setOnClickListener {

            if (searchItem.liked) {
                searchItem.liked = false
                binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)

            } else {
                searchItem.liked = true
                onGitItemClick(searchItem)
                binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_dark)
            }

            if (searchItem.liked) {
                onFavoriteClick(searchItem.toFavoriteMap())
            }
        }


    }
}