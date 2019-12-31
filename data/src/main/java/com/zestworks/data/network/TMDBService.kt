package com.zestworks.data.network

import com.zestworks.data.model.MovieListWireObject
import retrofit2.http.GET

interface TMDBService {
    @GET("/3/discover/movie?sort_by=popularity.desc&page=1")
    suspend fun discoverPopularMovies(): MovieListWireObject
}