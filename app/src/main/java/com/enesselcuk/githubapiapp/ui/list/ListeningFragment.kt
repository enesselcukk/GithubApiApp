package com.enesselcuk.githubapiapp.ui.list


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesselcuk.githubapiapp.base.BaseFragment
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.enesselcuk.githubapiapp.data.remote.model.searchModel.Item
import com.enesselcuk.githubapiapp.databinding.FragmentListeningBinding
import com.enesselcuk.githubapiapp.ui.list.listAdapter.ListeningAdapter
import com.enesselcuk.githubapiapp.util.collectLast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListeningFragment :
    BaseFragment<FragmentListeningBinding>(FragmentListeningBinding::inflate) {
    private val viewModel: ListViewModel by activityViewModels()
    private lateinit var listAdapter: ListeningAdapter

    override fun definition() {
        listAdapter = ListeningAdapter(
            ::onDetailClick,
            ::onGitItemClick,
            ::insertFav,
        )
        with(binding) {
            recyclerviewList.layoutManager = LinearLayoutManager(requireContext())
            recyclerviewList.adapter = listAdapter

            searchImage.setOnClickListener {
                val action = ListeningFragmentDirections.actionListeningFragmentToSearchFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.allGithub()
    }

    override fun setObserver() {
        collectLast(viewModel.uiState, ::uiState)
    }

    private fun uiState(uiState: ListUiState) {
        listAdapter.submitList(uiState.item)
        binding.progresbarList.isVisible = uiState.isLoading
        binding.textState.text = uiState.isError
    }


    override fun onResume() {
        super.onResume()
        viewModel.allGithub()
    }

    private fun onDetailClick(item: Item) {
        val action = ListeningFragmentDirections.actionListeningFragmentToDetailFragment(item)
        findNavController().navigate(action)
    }

    private fun onGitItemClick(item: Item) {
        viewModel.setGitItem(item)
    }

    private fun insertFav(fav: FavoriteEntity) {
        viewModel.insertFav(fav)
        Toast.makeText(
            requireContext(),
            "${fav.login} saved to favorite",
            Toast.LENGTH_SHORT
        ).show()
    }

}