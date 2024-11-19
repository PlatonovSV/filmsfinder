package com.praions.filmsfinder.model

data class FilmDetail(
    val id: Long,
    val localizedName: String?,
    val name: String?,
    val year: Int?,
    val rating: Float?,
    val imageUrl: String?,
    val description: String,
    val genres: List<Int>
) {
    companion object {
        fun fromDto(dto: FilmDetailDto, genres: List<GenreData>): FilmDetail? =
            if (dto.id != null) {
                FilmDetail(
                    id = dto.id,
                    localizedName = dto.localizedName,
                    name = dto.name,
                    year = dto.year,
                    rating = dto.rating,
                    imageUrl = dto.imageUrl,
                    description = dto.description ?: "",
                    genres = dto.genres?.mapNotNull { genres.find { genre -> genre.name == it }?.id }
                        ?: emptyList()
                )
            } else {
                null
            }
    }
}

