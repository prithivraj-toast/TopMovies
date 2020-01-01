package com.zestworks.movielist

import com.zestworks.common.Data
import com.zestworks.common.Data.Error
import com.zestworks.common.Data.Success
import com.zestworks.data.db.MovieDAO
import com.zestworks.data.model.Movie
import com.zestworks.data.network.TMDBService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class OfflineFirstMovieListRepository(
    private val dao: MovieDAO,
    private val tmdbService: TMDBService
) : MovieListRepository {
    override fun getMovies(): Flow<Data<List<Movie>>> {
        val networkResultFlow: Flow<Data<List<Movie>>> = flow {
            try {
                tmdbService.discoverPopularMovies().apply {
                    dao.addMoviesList(movies)
                    emit(Success(movies))
                }
            } catch (exception: Exception) {
                emit(Error(exception.localizedMessage ?: "Unknown error!"))
            }
        }

        val roomResultFlow: Flow<Data<List<Movie>>> = dao.getMovieList().map {
            Success(it)
        }

        return roomResultFlow.combine(networkResultFlow) { local, network ->
            if (network is Error && (local as Success).data.isEmpty()) {
                network
            } else {
                local
            }
        }
    }
}