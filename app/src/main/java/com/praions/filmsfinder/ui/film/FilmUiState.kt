package com.praions.filmsfinder.ui.film

data class FilmUiState(
    val localizedName: String = "",
    val name: String = "",
    val year: String = "",
    val rating: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val genres: List<String> = listOf()
)
