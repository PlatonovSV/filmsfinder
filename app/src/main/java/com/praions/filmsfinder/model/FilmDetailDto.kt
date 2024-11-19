package com.praions.filmsfinder.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDetailDto(
    val id: Long? = null,
    @SerialName("localized_name")
    val localizedName: String? = null,
    val name: String? = null,
    val year: Int? = null,
    val rating: Float? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    val description: String? = null,
    val genres: List<String>? = null
)