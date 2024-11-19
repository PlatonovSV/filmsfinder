package com.praions.filmsfinder.ui.search

import com.praions.filmsfinder.network.ErrorType
import com.praions.filmsfinder.network.NetworkRequestState

data class SearchUiState(
    val networkRequestState: NetworkRequestState = NetworkRequestState.LOADING,
    val errorType: ErrorType? = null
)
