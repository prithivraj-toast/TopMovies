package com.zestworks.moviedetail.mockdata

import com.google.gson.Gson
import com.zestworks.common.Data
import com.zestworks.data.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File

val file = File("src/test/java/com/zestworks/moviedetail/mockdata/dummydata.json")
const val DUMMY_ERROR = "Network down"

val dummyResponse: MovieDetail =
    Gson().fromJson(file.readText(), MovieDetail::class.java)

val goodFlow: Flow<Data<MovieDetail>> = flowOf(
    Data.Success(
        dummyResponse
    )
)

val errorFlow: Flow<Data<MovieDetail>> = flowOf(
    Data.Error(DUMMY_ERROR)
)

val offlineFlow: Flow<Data<MovieDetail>> = flowOf(
    Data.Success(
        dummyResponse
    ),
    Data.Error(DUMMY_ERROR)
)