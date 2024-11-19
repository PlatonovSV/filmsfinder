package com.praions.filmsfinder

import android.app.Application
import com.praions.filmsfinder.di.networkModule
import com.praions.filmsfinder.di.repositoryModule
import com.praions.filmsfinder.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FilmsFinderApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FilmsFinderApplication)
            androidLogger()
            modules(networkModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }
    }
}