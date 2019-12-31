package com.zestworks.common

/*
 * This is a ViewModel bound class that tells the state of the current data fetch operation.
 * Error is usually for network requests that have failed.
 */
sealed class Data<out T : Any> {
    data class Success<out T : Any>(val data: T) : Data<T>()
    data class Error(val errorMessage: String) : Data<Nothing>()
}