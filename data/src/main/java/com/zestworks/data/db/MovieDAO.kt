package com.zestworks.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zestworks.data.model.Movie
import com.zestworks.data.model.MovieDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * FROM Movie")
    fun getMovieList(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviesList(listOfMovies: List<Movie>)

    @Query("SELECT * FROM MovieDetail where id=:movieID")
    fun getMovieDetail(movieID: Int): Flow<MovieDetail?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieDetail(movieDetail: MovieDetail)
}