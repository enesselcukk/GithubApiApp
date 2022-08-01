package com.enesselcuk.githubapiapp.ui.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.domain.GithubRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val githubRepos: GithubRepos) : ViewModel() {
    private val _uiState = MutableStateFlow<PagingData<Item>>(PagingData.empty())
    val uiState: StateFlow<PagingData<Item>> = _uiState

    private var job: Job? = null

    fun search(name: String) {
        job?.cancel()
        job = viewModelScope.launch {
            githubRepos.getSearch(name).cachedIn(viewModelScope)
                .collect {
                    _uiState.value = it
                }
        }
    }

}


