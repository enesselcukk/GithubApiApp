package com.enesselcuk.githubapiapp.common

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.enesselcuk.githubapiapp.databinding.UnsplashloadBinding

class UnSplashViewHolder(private val binding: UnsplashloadBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState, retry: () -> Unit) {
        binding.footerUiState = UnSplashUiState(loadState)
        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

}