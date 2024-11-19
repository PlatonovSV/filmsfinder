package com.praions.filmsfinder.di

import com.praions.filmsfinder.ui.film.FilmViewModel
import com.praions.filmsfinder.ui.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::FilmViewModel)
}