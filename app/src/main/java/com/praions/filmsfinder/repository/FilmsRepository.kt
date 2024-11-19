package com.praions.filmsfinder.repository

import com.praions.filmsfinder.model.FilmCard
import com.praions.filmsfinder.model.FilmDetail
import com.praions.filmsfinder.model.GenreData
import com.praions.filmsfinder.network.NetworkRequestInfo

interface FilmsRepository {
    fun findFilmsByGenre(genreId: Int): List<FilmCard>
    fun getGenres(): List<GenreData>
    fun getAllFilms(): List<FilmCard>
    fun getFilmById(filmId: Long): FilmDetail?
    fun getGenresByIds(genreIds: List<Int>): List<GenreData>
    suspend fun fetchFilms(): NetworkRequestInfo
    fun isDataLoading(): Boolean
}