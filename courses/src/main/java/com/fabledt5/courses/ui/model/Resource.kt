package com.fabledt5.courses.ui.model

sealed class Resource<out T> {
    class Success<T>(data: T) : Resource<T>()
    class Error<T>(error: Throwable) : Resource<T>()
    object Loading: Resource<Nothing>()
}
