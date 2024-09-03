package com.example.newsapp.presentation.ui

sealed class NewsUiState<out T> {

     object Loading : NewsUiState<Nothing>()
    data class Success<T>(val data: T) : NewsUiState<T>()
    data class Error(val message: String) : NewsUiState<Nothing>()

    fun toData(): T? {
        return if (this is Success) {
            data
        } else {
            null
        }
    }
}