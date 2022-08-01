package com.enesselcuk.githubapiapp.ui.search

import android.content.Context
import androidx.paging.LoadState
import com.enesselcuk.githubapiapp.R
import com.enesselcuk.githubapiapp.common.BaseUiState

data class SearchUiState(
    private val loadState: LoadState
) : BaseUiState() {

    fun getProgressBarVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)

    fun getListVisibility() = getViewVisibility(isVisible = loadState is LoadState.NotLoading)

    fun getErrorVisibility() = getViewVisibility(isVisible = loadState is LoadState.Error)

    fun getErrorMessage(context: Context) = if (loadState is LoadState.Error) {
        loadState.error.localizedMessage ?: context.getString(R.string.something_went_wrong)
    } else ""
}


