package com.praions.filmsfinder.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praions.filmsfinder.model.FilmCard
import com.praions.filmsfinder.model.Genre
import com.praions.filmsfinder.network.NetworkRequestState
import com.praions.filmsfinder.repository.FilmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.List

class SearchViewModel(val repository: FilmsRepository) : ViewModel() {


    private var _genres: MutableLiveData<List<Genre>> = MutableLiveData<List<Genre>>(listOf())
    val genres: LiveData<List<Genre>> = _genres

    private var _filmCards = MutableLiveData<List<FilmCard>>(listOf())
    val filmCards: LiveData<List<FilmCard>> = _filmCards

    private var _uiState = MutableLiveData<SearchUiState>(SearchUiState())
    val uiState: LiveData<SearchUiState> = _uiState

    init {
        loadFilms()
    }

    fun loadFilms() {
        _uiState.postValue(SearchUiState(networkRequestState = NetworkRequestState.LOADING))
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.fetchFilms()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    updateUiState(
                        repository.getGenres().map { Genre.fromGenreData(it) },
                        repository.getAllFilms()
                    )
                }
            } else {
                withContext(Dispatchers.Main) {
                    _uiState.postValue(
                        SearchUiState(
                            networkRequestState = response.state,
                            errorType = response.errorType
                        )
                    )
                }
            }
        }
    }

    fun selectGenre(genreId: Int) {
        val selectedGenre = _genres.value?.find { it.id == genreId }
        if (selectedGenre != null) {
            val updatedFilms: List<FilmCard>
            val updatedGenres: List<Genre>
            if (selectedGenre.isSelected) {
                updatedGenres = _genres.value?.map {
                    if (it.id == genreId) {
                        it.copy(isSelected = false)
                    } else {
                        it
                    }
                } ?: emptyList()

                updatedFilms = repository.getAllFilms()
            } else {
                updatedGenres = _genres.value?.map {
                    if (it.id == genreId || it.isSelected) {
                        it.copy(isSelected = it.id == genreId)
                    } else {
                        it
                    }
                } ?: emptyList()

                updatedFilms = repository.findFilmsByGenre(genreId)
            }
            updateUiState(updatedGenres, updatedFilms)
        }

    }

    fun updateUiState(genres: List<Genre>, filmCards: List<FilmCard>) {
        val sortedGenres = genres.sortedBy { it.name }
        val sortedFilmCards = filmCards.sortedBy { it.name ?: "" }
        _genres.postValue(sortedGenres)
        _filmCards.postValue(sortedFilmCards)
        _uiState.postValue(SearchUiState(networkRequestState = NetworkRequestState.SUCCESS))
    }


}