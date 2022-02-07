package com.fabledt5.courses.ui.model

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: Throwable) : Resource<T>()
    object Loading: Resource<Nothing>()
}
