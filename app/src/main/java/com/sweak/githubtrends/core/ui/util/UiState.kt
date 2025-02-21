package com.sweak.githubtrends.core.ui.util

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val errorMessage: UiText) : UiState<Nothing>()
}
