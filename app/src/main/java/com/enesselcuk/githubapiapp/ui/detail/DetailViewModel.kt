package com.enesselcuk.githubapiapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.remote.model.detailResponse.DetailResponse
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.domain.GithubRepos
import com.enesselcuk.githubapiapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepos: GithubRepos
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState(isLoading = false))
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<UiEvent.ShowText>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null


    fun getDetail(name: String) {
        job = viewModelScope.launch {
            detailRepos.getDetail(name)
                .collectLatest { network ->
                    when (network) {
                        is NetworkResult.Error -> {
                            handleFavoriteError(network)
                            _eventFlow.emit(
                                UiEvent.ShowText(
                                    network.message ?: "Unknown Error", isVisible = false
                                )
                            )
                        }
                        is NetworkResult.Loading -> {
                            handleFavoriteLoading(network)
                        }
                        is NetworkResult.Success -> {
                            handleFavoriteSuccess(network)
                        }
                    }
                }
        }
    }

    private fun handleFavoriteSuccess(networkResult: NetworkResult.Success<List<DetailResponse>>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                detailResponse = networkResult.data ?: emptyList(),

                )
        }
    }

    private fun handleFavoriteError(networkResult: NetworkResult.Error<List<DetailResponse>>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                detailResponse = networkResult.data ?: emptyList(),
            )
        }
    }

    private fun handleFavoriteLoading(networkResult: NetworkResult.Loading<List<DetailResponse>>) {
        _uiState.update {
            it.copy(isLoading = true, detailResponse = networkResult.data ?: emptyList())
        }
    }


    fun setGitItemClick(item: Item) {
        viewModelScope.launch {
            detailRepos.setGit(item)
        }
    }

    fun insertFavClick(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            detailRepos.insertFav(favoriteEntity)
        }
    }


}

sealed class UiEvent {
    data class ShowText(val message: String, val isVisible: Boolean) : UiEvent()
}