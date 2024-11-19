package com.praions.filmsfinder.repository

import com.praions.filmsfinder.model.FilmCard
import com.praions.filmsfinder.model.FilmDetail
import com.praions.filmsfinder.model.GenreData
import com.praions.filmsfinder.network.NetworkRequestInfo
import com.praions.filmsfinder.network.NetworkRequestState

class FilmsRepositoryImpl(
    val localRepository: FilmsLocalRepository,
    val remoteRepository: FilmsRemoteRepository
) : FilmsRepository {
    override fun findFilmsByGenre(genreId: Int): List<FilmCard> =
        localRepository.findFilmsByGenre(genreId)

    override fun getGenres() = localRepository.getGenres()

    override fun getAllFilms(): List<FilmCard> = localRepository.getAllFilms()

    override fun getFilmById(filmId: Long): FilmDetail? = localRepository.getFilmById(filmId)

    override fun getGenresByIds(genreIds: List<Int>) =
        localRepository.getGenresByIds(genreIds)

    override suspend fun fetchFilms(): NetworkRequestInfo {
        val response = remoteRepository.fetchFilmDetails()
        if (response.state == NetworkRequestState.SUCCESS && response.result != null) {
            val data = response.result
            val genres = mutableListOf<GenreData>()
            data.forEach {
                it.genres?.forEach { genre ->
                    if (!genres.any { it.name == genre }) {
                        genres.add(GenreData(genres.size, genre))
                    }
                }
            }

            val films = data.mapNotNull {
                FilmDetail.Companion.fromDto(it, genres)
            }

            localRepository.updateGenres(genres)
            localRepository.updateFilms(films)
        }

        return response.getInfo()
    }

    override fun isDataLoading(): Boolean = localRepository.isDataLoading()

}
