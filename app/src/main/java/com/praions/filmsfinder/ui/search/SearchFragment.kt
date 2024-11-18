package com.praions.filmsfinder.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.praions.filmsfinder.R
import com.praions.filmsfinder.adapters.FilmAdapter
import com.praions.filmsfinder.adapters.GenreAdapter
import com.praions.filmsfinder.adapters.TitleAdapter
import com.praions.filmsfinder.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val viewModel: SearchViewModel = SearchViewModel()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        val genresAdapter = GenreAdapter(
            onClick = {}
        )

        val filmsAdapter = FilmAdapter(
            onClick = {}
        )
        val titleFilmsAdapter = TitleAdapter()
        titleFilmsAdapter.submitList(listOf(Title(1, getString(R.string.films))))
        val titleGenresAdapter = TitleAdapter()
        titleGenresAdapter.submitList(listOf(Title(0, getString(R.string.genres))))

        val concatAdapter =
            ConcatAdapter(titleGenresAdapter, genresAdapter, titleFilmsAdapter, filmsAdapter)
        binding.films.adapter = concatAdapter


        viewModel.filmCards.observe(viewLifecycleOwner) {
            filmsAdapter.submitList(it)
        }
        viewModel.genres.observe(viewLifecycleOwner) {
            genresAdapter.submitList(it)
        }

        val layoutManager = FilmsFinderLayoutManager(context)
        layoutManager.setTwoElementsBeginningIndex(22)
        binding.films.layoutManager = layoutManager


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = binding
    }
}