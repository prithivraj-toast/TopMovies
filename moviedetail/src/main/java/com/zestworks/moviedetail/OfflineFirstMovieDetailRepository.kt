package com.zestworks.moviedetail

import com.zestworks.common.Data
import com.zestworks.common.Data.Empty
import com.zestworks.common.Data.Error
import com.zestworks.common.Data.Success
import com.zestworks.data.db.MovieDAO
import com.zestworks.data.model.MovieDetail
import com.zestworks.data.network.TMDBService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class OfflineFirstMovieDetailRepository(
    private val dao: MovieDAO,
    private val tmdbService: TMDBService
) : MovieDetailRepository {

    override fun getMovieDetail(movieID: Int): Flow<Data<MovieDetail>> {
        val networkResultFlow: Flow<Data<MovieDetail>> = flow {
            try {
                tmdbService.getMovieDetails(movieID).apply {
                    dao.addMovieDetail(this)
                    emit(Success(this))
                }
            } catch (exception: Exception) {
                emit(Error(exception.localizedMessage ?: "Unknown error!"))
            }
        }

        val roomResultFlow: Flow<Data<MovieDetail>> = dao.getMovieDetail(movieID).map {
            if (it == null) {
                Empty
            } else {
                Success(it)
            }
        }

        return roomResultFlow.combine(networkResultFlow) { local, network ->
            if (network is Error && local is Empty) {
                network
            } else {
                local
            }
        }
    }
}