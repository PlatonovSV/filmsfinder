package com.praions.filmsfinder.di

import com.praions.filmsfinder.repository.FilmsLocalRepository
import com.praions.filmsfinder.repository.FilmsRemoteRepository
import com.praions.filmsfinder.repository.FilmsRepository
import com.praions.filmsfinder.repository.FilmsRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::FilmsRemoteRepository)
    singleOf(::FilmsLocalRepository)
    singleOf(::FilmsRepositoryImpl) { bind<FilmsRepository>() }
}
