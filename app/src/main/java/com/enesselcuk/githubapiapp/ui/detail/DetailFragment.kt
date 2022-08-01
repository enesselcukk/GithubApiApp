package com.enesselcuk.githubapiapp.ui.detail


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.enesselcuk.githubapiapp.R
import com.enesselcuk.githubapiapp.base.BaseFragment
import com.enesselcuk.githubapiapp.databinding.FragmentDetailBinding
import com.enesselcuk.githubapiapp.util.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel: DetailViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun setObserver() {
        collectLast(viewModel.uiState, ::uiState)
        collectLast(viewModel.eventFlow, ::uiEvent)
    }

    override fun definition() {
        val name = args.detail
        name.login?.let { login ->
            viewModel.getDetail(login)
        }
        liked()
    }

    private fun liked() {

        with(binding) {
            val likedArgs = args.detail

            likedArgs.liked.let {
                if (it) {
                    binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_dark)
                } else {
                    binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)
                }
            }

            imageFavorite.setOnClickListener {
                likedArgs.liked.let {
                    if (it) {
                        likedArgs.liked = false
                        binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)

                    } else {
                        likedArgs.liked = true
                        viewModel.setGitItemClick(likedArgs)
                        binding.imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_dark)
                    }

                    if (likedArgs.liked) {
                        viewModel.insertFavClick(likedArgs.toFavoriteMap())
                        Toast.makeText(
                            requireContext(),
                            "${likedArgs.login} saved to favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun uiState(uiState: DetailUiState) {
        binding.setUiState = uiState
        uiState.detailResponse?.map {
            binding.detailResponse = it
        }
    }

    private fun uiEvent(uiEvent: UiEvent.ShowText) {
        binding.uiEvent = uiEvent
        binding.progressBar.isVisible = uiEvent.isVisible
    }

}
