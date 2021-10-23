package com.example.mvijsonholder.common

import java.lang.Exception

sealed interface DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>
    data class Error(val exception: Exception) : DataState<Nothing>
    object Loading : DataState<Nothing>
    object Empty : DataState<Nothing>
}
