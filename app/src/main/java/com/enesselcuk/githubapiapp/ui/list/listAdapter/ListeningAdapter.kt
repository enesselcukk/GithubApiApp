package com.enesselcuk.githubapiapp.ui.list.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.enesselcuk.githubapiapp.R
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item


class ListeningAdapter(
    private val onClick: (Item) -> Unit,
    private val onGitItemClick: (Item) -> Unit,
    private val insertFavClick: (FavoriteEntity) -> Unit,


    ) :
    ListAdapter<Item, ListeningVHolder>(ListeningDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeningVHolder =
        ListeningVHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list, parent, false
            )
        )

    override fun onBindViewHolder(holder: ListeningVHolder, position: Int) {
        val basketPosition = getItem(position)
        if (basketPosition != null) {
            holder.bind(
                basketPosition,
                onClick,
                onGitItemClick,
                insertFavClick,

            )
        }
    }
}