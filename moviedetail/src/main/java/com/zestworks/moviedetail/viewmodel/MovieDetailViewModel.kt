package com.zestworks.moviedetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zestworks.common.Data.Empty
import com.zestworks.common.Data.Error
import com.zestworks.common.Data.Success
import com.zestworks.common.LCE
import com.zestworks.moviedetail.domain.MovieDetailRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class MovieDetailViewModel(private val repository: MovieDetailRepository) : ViewModel() {
    fun viewState(id: Int) = repository.getMovieDetail(id)
        .map {
            when (it) {
                is Success -> {
                    LCE.Content(it.data)
                }
                is Error -> {
                    LCE.Error(it.errorMessage)
                }
                is Empty -> {
                    LCE.Loading
                }
            }
        }
        .onStart { emit(LCE.Loading) }
        .distinctUntilChanged()
        .asLiveData()
}