package com.enesselcuk.githubapiapp.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesselcuk.githubapiapp.R
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.domain.GithubRepos
import com.enesselcuk.githubapiapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repos: GithubRepos,
    private val context: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState(true))
    val uiState: StateFlow<ListUiState> = _uiState

    fun allGithub() {
        viewModelScope.launch {
            repos.getAllGithub().collectLatest { networkState ->
                when (networkState) {
                    is NetworkResult.Loading -> {
                        handleFavoriteLoading(networkState)
                    }
                    is NetworkResult.Success -> {
                        handleFavoriteSuccess(networkState)

                    }
                    is NetworkResult.Error -> {
                        handleFavoriteError(networkState)

                    }
                }
            }
        }
    }


    fun setGitItem(item: Item) {
        viewModelScope.launch {
            repos.setGit(item)
        }
    }


    fun insertFav(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            repos.insertFav(favoriteEntity)
        }
    }


    private fun handleFavoriteSuccess(networkResult: NetworkResult.Success<List<Item>>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                item = networkResult.data ?: emptyList()
            )
        }
    }

    private fun handleFavoriteError(networkResult: NetworkResult.Error<List<Item>>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                item = networkResult.data ?: emptyList(),
                isError = networkResult.message ?: context.getString(R.string.something_went_wrong)
            )
        }
    }

    private fun handleFavoriteLoading(networkResult: NetworkResult.Loading<List<Item>>) {
        _uiState.update {
            it.copy(isLoading = true, item = networkResult.data ?: emptyList())
        }
    }
}