package com.enesselcuk.githubapiapp.ui.search


import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesselcuk.githubapiapp.base.BaseFragment
import com.enesselcuk.githubapiapp.common.UnSplashLoadAdapter
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
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
        searchAdapter = SearchAdapter(::onClickDetail)

        with(binding) {

            recyclerview.layoutManager = LinearLayoutManager(requireContext())
            recyclerview.adapter =
                searchAdapter.withLoadStateFooter(UnSplashLoadAdapter(searchAdapter::retry))
            recyclerview.setHasFixedSize(true)

            searcView.setIconifiedByDefault(true)
            searcView.isIconified = false
        }

        setAdapter()
        searchApi()
        binding.searchClick = this
    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
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
            searchAdapter.withLoadStateFooter(
                footer = UnSplashLoadAdapter(searchAdapter::retry)
            )
    }

    private fun setSearchUiState(loadState: LoadState) {
        binding.setUiState = SearchUiState(loadState)
    }

    private suspend fun setUsers(users: PagingData<Item>) {
        searchAdapter.submitData(users)
    }

    fun searchApi() {
        binding.searcView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (query.isNotEmpty() && query.length >= 2) {
                        viewModel.search(query)
                        binding.recyclerview.scrollToPosition(0)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun onClickDetail(item: Item) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(item)
        findNavController().navigate(action)
    }

}
