package com.praions.filmsfinder.network

import com.praions.filmsfinder.model.FilmDetailDto
import com.praions.filmsfinder.model.FilmListDto
import retrofit2.http.GET

/**
 * public интерфейс, предоставляющий метод [getFilmDetails]
 */
interface FilmsFinderApiService {
    /**
     * Возвращает [FilmListDto], что является обёрткой списка [FilmDetailDto]. Этот метод можно вызвать из сопрограммы.
     * Аннотация @GET указывает, что конечная точка "sequeniatesttask/films.json" будет запрашиваться с помощью метода HTTP GET
     */
    @GET("sequeniatesttask/films.json")
    suspend fun getFilmDetails(): FilmListDto

}