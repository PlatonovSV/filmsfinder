package com.praions.filmsfinder.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.praions.filmsfinder.network.FilmsFinderApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
  single {
    OkHttpClient.Builder()
      .build()
  }
  single {
    Retrofit.Builder()
      .client(get<OkHttpClient>())
      .baseUrl("https://s3-eu-west-1.amazonaws.com/")
      .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
      .build()
  }
  single { get<Retrofit>().create(FilmsFinderApiService::class.java) }

}
