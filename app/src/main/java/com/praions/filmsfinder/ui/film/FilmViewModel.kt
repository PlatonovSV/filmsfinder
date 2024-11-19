package com.praions.filmsfinder.ui.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.praions.filmsfinder.model.FilmDetail
import com.praions.filmsfinder.repository.FilmsRepository

class FilmViewModel(val repository: FilmsRepository) : ViewModel() {
    var uiState: FilmUiState by mutableStateOf(FilmUiState())
        private set

    private var _needNavUp = MutableLiveData<Boolean>(false)
    val needNavUp: LiveData<Boolean> = _needNavUp

    fun getFilm(id: Long) {
        val film = repository.getFilmById(id)
        updateUiState(film)
    }

    private fun updateUiState(film: FilmDetail?) {
        val oldUiState = uiState
        val genres = repository.getGenresByIds(film?.genres ?: listOf())
        val newValue = oldUiState.copy(localizedName = film?.localizedName ?: "",
            name = film?.name ?: "",
            year = (film?.year ?: "").toString(),
            rating = (film?.rating ?: "").toString(),
            imageUrl = film?.imageUrl ?: "",
            description = film?.description ?: "",
            genres = genres.map { it.name })
        uiState = newValue
    }

    fun navigateUp() {
        _needNavUp.postValue(true)
    }
}