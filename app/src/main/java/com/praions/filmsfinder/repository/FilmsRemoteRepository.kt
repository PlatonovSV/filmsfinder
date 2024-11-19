package com.praions.filmsfinder.repository

import android.util.Log
import com.praions.filmsfinder.model.FilmDetailDto
import com.praions.filmsfinder.network.ErrorType
import com.praions.filmsfinder.network.FilmsFinderApiService
import com.praions.filmsfinder.network.NetworkRequest
import com.praions.filmsfinder.network.NetworkRequestState
import retrofit2.HttpException
import java.io.IOException

class FilmsRemoteRepository(private val filmsFinderApiService: FilmsFinderApiService) {
    suspend fun fetchFilmDetails(): NetworkRequest<List<FilmDetailDto>> {
        var result: NetworkRequest<List<FilmDetailDto>> = try {
            NetworkRequest(NetworkRequestState.SUCCESS, filmsFinderApiService.getFilmDetails().films)
        } catch (_: IOException) {
            NetworkRequest(NetworkRequestState.ERROR, errorType = ErrorType.NO_INTERNET)
        } catch (_: HttpException) {
            NetworkRequest(NetworkRequestState.ERROR, errorType = ErrorType.SERVER)
        } catch (e: Exception) {
            Log.d("FilmsRemoteRepository", e.toString())
            NetworkRequest(NetworkRequestState.ERROR, errorType = ErrorType.SERVER)
        }
        return result
    }
}