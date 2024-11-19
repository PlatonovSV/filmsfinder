package com.praions.filmsfinder.repository

import com.praions.filmsfinder.model.FilmCard
import com.praions.filmsfinder.model.FilmDetail
import com.praions.filmsfinder.model.GenreData

class FilmsLocalRepository {
    private var films = listOf<FilmDetail>()
    private var genres = listOf<GenreData>()
    private var isDataLoading = false

    fun updateFilms(newFilms: List<FilmDetail>) {
        isDataLoading = true
        films = newFilms
    }

    fun updateGenres(newGenres: List<GenreData>) {
        genres = newGenres
    }

    fun findFilmsByGenre(genreId: Int): List<FilmCard> {
        val matchFilms = films.filter { it.genres.contains(genreId) }
        return matchFilms.map {
            FilmCard.fromFilmDetail(it)
        }
    }

    fun getGenres() = genres

    fun getAllFilms() = films.map { FilmCard.fromFilmDetail(it) }

    fun getFilmById(filmId: Long) = films.find { it.id == filmId }

    fun getGenresByIds(genreIds: List<Int>) = genres.filter { genreIds.contains(it.id) }

    fun isDataLoading() = isDataLoading
}
