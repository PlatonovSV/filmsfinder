package com.praions.filmsfinder.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.praions.filmsfinder.FilmsFinderToolbar
import com.praions.filmsfinder.R
import com.praions.filmsfinder.adapters.FilmAdapter
import com.praions.filmsfinder.adapters.GenreAdapter
import com.praions.filmsfinder.adapters.TitleAdapter
import com.praions.filmsfinder.databinding.FragmentSearchBinding
import com.praions.filmsfinder.model.Title
import com.praions.filmsfinder.network.NetworkRequestState
import com.praions.filmsfinder.ui.theme.FilmsFinderTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel: SearchViewModel by viewModel()

        binding.lifecycleOwner = viewLifecycleOwner

        val navToFilm = { id: Long ->
            val navAction = SearchFragmentDirections.actionSearchFragmentToFilmFragment(id)
            view.findNavController().navigate(navAction)
        }
        binding.films.adapter = setupConcatAdapter(
            onFilmClick = navToFilm,
            viewModel = viewModel
        )
        binding.films.layoutManager = setupLayoutManager(viewModel)

        binding.composeLoading.setContent {
            FilmsFinderTheme {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
        }

        binding.composeError.setContent {
            FilmsFinderTheme {
                ErrorScreen(
                    errorType = viewModel.uiState.value?.errorType,
                    retryAction = viewModel::loadFilms,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        binding.composeTopAppBar.setContent {
            FilmsFinderTheme {
                FilmsFinderToolbar(title = stringResource(R.string.films))
            }
        }

        setupUiStareObserver(viewModel)

        return view
    }

    private fun setupUiStareObserver(viewModel: SearchViewModel) {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it.networkRequestState) {
                NetworkRequestState.LOADING -> {
                    binding.composeLoading.visibility = View.VISIBLE
                    binding.composeError.visibility = View.GONE
                }

                NetworkRequestState.SUCCESS -> {
                    binding.composeLoading.visibility = View.GONE
                    binding.composeError.visibility = View.GONE
                }

                NetworkRequestState.ERROR -> {
                    binding.composeLoading.visibility = View.GONE
                    binding.composeError.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupLayoutManager(
        viewModel: SearchViewModel
    ): RecyclerView.LayoutManager {
        val currentOrientation = resources.configuration.orientation
        val layoutManager = FilmsFinderLayoutManager(context, currentOrientation)
        viewModel.genres.observe(viewLifecycleOwner) {
            layoutManager.setGenresCount(it.size)
        }

        return layoutManager
    }

    private fun setupConcatAdapter(
        onFilmClick: (Long) -> Unit,
        viewModel: SearchViewModel
    ): ConcatAdapter {
        val genresAdapter = GenreAdapter(
            onClick = viewModel::selectGenre
        )

        val filmsAdapter = FilmAdapter(onClick = onFilmClick)

        viewModel.filmCards.observe(viewLifecycleOwner) {
            filmsAdapter.submitList(it)
        }
        viewModel.genres.observe(viewLifecycleOwner) {
            genresAdapter.submitList(it)
        }

        val titleFilmsAdapter = TitleAdapter()
        titleFilmsAdapter.submitList(listOf(Title(1, getString(R.string.films))))
        val titleGenresAdapter = TitleAdapter()
        titleGenresAdapter.submitList(listOf(Title(0, getString(R.string.genres))))

        val concatAdapter =
            ConcatAdapter(titleGenresAdapter, genresAdapter, titleFilmsAdapter, filmsAdapter)
        return concatAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = binding
    }
}