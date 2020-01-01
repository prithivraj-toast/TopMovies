package com.zestworks.common

/*
 * This is a ViewModel bound class that tells the state of the current data fetch operation.
 * Error is usually for network requests that have failed.
 * Empty is when everything works well but there is no data at the source. Typically in an empty database.
 */

sealed class Data<out T : Any> {
    data class Success<out T : Any>(val data: T) : Data<T>()
    data class Error(val errorMessage: String) : Data<Nothing>()
    object Empty : Data<Nothing>()
}