package com.example.newsapp.domain.util

sealed class FetchActions<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : FetchActions<T>(data)

    class Error<T>(message: String, data: T? = null) : FetchActions<T>(data, message)
}