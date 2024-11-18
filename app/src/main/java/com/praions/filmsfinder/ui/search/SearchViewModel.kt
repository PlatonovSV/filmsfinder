package com.praions.filmsfinder.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private var _genres = MutableLiveData<List<Genre>>(
        listOf(
            Genre(1, "Action"),
            Genre(2, "Comedy"),
            Genre(3, "Drama"),
            Genre(4, "Horror"),
            Genre(5, "Romance"),
            Genre(6, "Action"),
            Genre(7, "Comedy"),
            Genre(8, "Drama"),
            Genre(9, "Horror"),
            Genre(10, "Romance"),
            Genre(11, "Action"),
            Genre(12, "Comedy"),
            Genre(13, "Drama"),
            Genre(14, "Horror"),
            Genre(15, "Romance", true),
            Genre(16, "Action"),
            Genre(17, "Comedy"),
            Genre(18, "Drama"),
            Genre(19, "Horror"),
            Genre(20, "Romance"),

            )
    )
    val genres: LiveData<List<Genre>> = _genres

    private var _filmCards = MutableLiveData<List<FilmCard>>(
        listOf(
            FilmCard(
                id = 1,
                imageUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                name = "Побег из Шоушенка"
            ),
            FilmCard(
                id = 2,
                name = "Побег из Шоушенка"
            ),
            FilmCard(
                id = 3,
                imageUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
            ),
            FilmCard(
                id = 4,
                name = "Побег из Шоушенка"
            ),
            FilmCard(
                id = 5,
                imageUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
            )
        )
    )
    val filmCards: LiveData<List<FilmCard>> = _filmCards

}