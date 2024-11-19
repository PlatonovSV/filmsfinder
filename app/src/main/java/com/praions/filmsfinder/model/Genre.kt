package com.praions.filmsfinder.model

data class Genre(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
) {
    companion object {
        fun fromGenreData(genreData: GenreData) = Genre(
            id = genreData.id,
            name = genreData.name,
            isSelected = false
        )
    }
}
