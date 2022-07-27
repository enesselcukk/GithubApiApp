package com.enesselcuk.githubapiapp.ui.search


import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesselcuk.githubapiapp.base.BaseFragment
import com.enesselcuk.githubapiapp.common.UnSplashLoadAdapter
import com.enesselcuk.githubapiapp.data.remote.model.Item
import com.enesselcuk.githubapiapp.databinding.FragmentSearchBinding
import com.enesselcuk.githubapiapp.ui.search.adapter.SearchAdapter
import com.enesselcuk.githubapiapp.util.collect
import com.enesselcuk.githubapiapp.util.collectLast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {


    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by activityViewModels()

    override fun definition() {
        searchAdapter = SearchAdapter()

        with(binding.recyclerview) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter.withLoadStateFooter(UnSplashLoadAdapter(searchAdapter::retry))
            setHasFixedSize(true)
        }

        setAdapter()
        searchApi()
    }


    override fun setObserver() {
        collectLast(viewModel.uiState, ::setUsers)
    }

    private fun setAdapter() {
        collect(flow = searchAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setSearchUiState
        )
        binding.recyclerview.adapter =
            searchAdapter.withLoadStateFooter(UnSplashLoadAdapter(searchAdapter::retry))
    }

    private fun setSearchUiState(loadState: LoadState) {
        binding.setUiState = SearchUiState(loadState)
    }

    private suspend fun setUsers(users: PagingData<Item>) {
        searchAdapter.submitData(users)
    }

    private fun searchApi() {
        binding.searcView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.recyclerview.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText.isNotEmpty() && newText.length >= 2) {
                        binding.recyclerview.scrollToPosition(0)
                        viewModel.search(newText)

                    }
                }
                return false
            }
        })
    }

}