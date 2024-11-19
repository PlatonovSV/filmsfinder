package com.praions.filmsfinder.ui.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.praions.filmsfinder.databinding.FragmentFilmBinding
import com.praions.filmsfinder.ui.theme.FilmsFinderTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class FilmFragment : Fragment() {
    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModel: FilmViewModel by viewModel()
        val filmId = FilmFragmentArgs.fromBundle(requireArguments()).filmId
        viewModel.getFilm(filmId)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.filmDetail.setContent {
            FilmsFinderTheme {
                FilmScreen(
                    uiState = viewModel.uiState,
                    onBackClick = { viewModel.navigateUp() },
                    canNavigateBack = true,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        viewModel.needNavUp.observe(viewLifecycleOwner) { needNavUp ->
            if (needNavUp) {
                view.findNavController().navigateUp()
            }
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = binding
    }
}