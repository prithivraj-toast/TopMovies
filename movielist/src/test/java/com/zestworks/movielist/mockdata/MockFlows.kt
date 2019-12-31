package com.zestworks.movielist.mockdata

import com.google.gson.Gson
import com.zestworks.common.Data
import com.zestworks.data.model.Movie
import com.zestworks.data.model.MovieListWireObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

val file = File("src/test/java/com/zestworks/movielist/mockdata/samedata.json")
const val DUMMY_ERROR = "Network down"

val dummyResponse: MovieListWireObject =
    Gson().fromJson(file.readText(), MovieListWireObject::class.java)

val goodFlow: Flow<Data<List<Movie>>> = flowOf(
    Data.Success(
        dummyResponse.movies
    )
)

val errorFlow: Flow<Data<List<Movie>>> = flowOf(
    Data.Error(DUMMY_ERROR)
)

val offlineFlow: Flow<Data<List<Movie>>> = flowOf(
    Data.Success(
        dummyResponse.movies
    ),
    Data.Error(DUMMY_ERROR)
)