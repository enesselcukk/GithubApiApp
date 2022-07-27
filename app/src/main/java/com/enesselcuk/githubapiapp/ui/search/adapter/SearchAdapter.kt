package com.enesselcuk.githubapiapp.ui.search.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.enesselcuk.githubapiapp.R
import com.enesselcuk.githubapiapp.data.remote.model.Item
import javax.inject.Inject


class SearchAdapter () :
    PagingDataAdapter<Item, SearchVHolder>(SearchDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVHolder =
        SearchVHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search, parent, false
            )
        )

    override fun onBindViewHolder(holder: SearchVHolder, position: Int) {
        val basketPosition = getItem(position)
        if (basketPosition != null) {
            holder.bind(basketPosition)
        }
    }

//    class ClickToBasket(val btnClick: (BasketResponseItem) -> Unit) {
//        fun clickBtn(basketResponseItem: BasketResponseItem) = btnClick(basketResponseItem)
//    }
}