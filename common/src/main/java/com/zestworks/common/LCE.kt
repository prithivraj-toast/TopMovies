package com.zestworks.common

/*
 * L - Loading
 * C - Content
 * E - Error
 *
 * This class is used to tell the UI about the current status of the information to be displayed.
 * Error can be used intrusively or not depending on the feature and nature of failure.
 * An error type can be added here for that purpose.
 */

sealed class LCE<out T : Any> {
    object Loading : LCE<Nothing>()
    data class Content<out T : Any>(val data: T) : LCE<T>()
    data class Error(val errorMessage: String) : LCE<Nothing>()
}