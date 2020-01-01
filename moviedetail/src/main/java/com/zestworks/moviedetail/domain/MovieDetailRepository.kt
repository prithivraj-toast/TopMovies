package com.zestworks.moviedetail.domain

import com.zestworks.common.Data
import com.zestworks.data.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getMovieDetail(movieID: Int): Flow<Data<MovieDetail>>
}