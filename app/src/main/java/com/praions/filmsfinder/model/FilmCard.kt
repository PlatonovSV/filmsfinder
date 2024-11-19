package com.praions.filmsfinder.model

data class FilmCard(
    val id: Long,
    val name: String? = null,
    val imageUrl: String? = null
) {
    companion object {
        fun fromFilmDetail(filmDetail: FilmDetail) =
            FilmCard(
                id = filmDetail.id,
                name = if (filmDetail.localizedName != null) filmDetail.localizedName else filmDetail.name,
                imageUrl = filmDetail.imageUrl
            )
    }
}
