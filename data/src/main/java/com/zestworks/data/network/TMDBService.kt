package com.zestworks.data.network

import com.zestworks.data.model.MovieDetail
import com.zestworks.data.model.MovieListWireObject
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBService {
    @GET("/3/discover/movie?sort_by=popularity.desc&page=1")
    suspend fun discoverPopularMovies(): MovieListWireObject

    @GET("/3/movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieID: Int): MovieDetail
}