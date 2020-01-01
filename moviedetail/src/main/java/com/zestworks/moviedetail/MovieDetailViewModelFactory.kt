package com.zestworks.moviedetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zestworks.data.getMoviesDAO
import com.zestworks.data.getTmdbService
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(
                OfflineFirstMovieDetailRepository(
                    dao = getMoviesDAO(context),
                    tmdbService = getTmdbService()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}