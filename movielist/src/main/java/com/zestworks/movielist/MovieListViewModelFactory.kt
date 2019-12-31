package com.zestworks.movielist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zestworks.data.getMoviesDAO
import com.zestworks.data.getTmdbService
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(
                OfflineFirstMovieListRepository(
                    dao = getMoviesDAO(context),
                    tmdbService = getTmdbService()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}