package com.praions.filmsfinder.model

import kotlinx.serialization.Serializable

@Serializable
data class FilmListDto(
    val films: List<FilmDetailDto>
)
