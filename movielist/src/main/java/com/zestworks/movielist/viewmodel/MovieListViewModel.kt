package com.zestworks.movielist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zestworks.common.Data
import com.zestworks.common.LCE
import com.zestworks.data.model.Movie
import com.zestworks.movielist.domain.MovieListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class MovieListViewModel(movieListRepository: MovieListRepository) : ViewModel() {
    val viewState: LiveData<LCE<List<Movie>>> = movieListRepository.getMovies()
        .map {
            when (it) {
                is Data.Success -> {
                    LCE.Content(it.data)
                }
                is Data.Error -> {
                    LCE.Error(it.errorMessage)
                }
                Data.Empty -> {
                    LCE.Loading
                }
            }
        }
        .onStart { emit(LCE.Loading) }
        .distinctUntilChanged()
        .asLiveData()
}