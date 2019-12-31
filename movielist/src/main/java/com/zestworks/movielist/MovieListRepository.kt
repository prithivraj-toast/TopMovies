package com.zestworks.movielist

import com.zestworks.common.Data
import com.zestworks.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    fun getMovies(): Flow<Data<List<Movie>>>
}