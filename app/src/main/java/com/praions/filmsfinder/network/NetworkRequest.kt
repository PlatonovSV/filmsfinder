package com.praions.filmsfinder.network

data class NetworkRequest<T>(
    val state: NetworkRequestState,
    val result: T? = null,
    val errorType: ErrorType? = null
) {
    fun getInfo() = NetworkRequestInfo(state, errorType)
}

enum class NetworkRequestState {
    LOADING,
    SUCCESS,
    ERROR,
}

enum class ErrorType {
    SERVER,
    NO_INTERNET,
}

data class NetworkRequestInfo(
    val state: NetworkRequestState,
    val errorType: ErrorType? = null
) {
    val isSuccessful = state == NetworkRequestState.SUCCESS
}