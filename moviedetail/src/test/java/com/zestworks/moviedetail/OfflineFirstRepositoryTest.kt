package com.zestworks.moviedetail

import com.zestworks.common.Data
import com.zestworks.data.db.MovieDAO
import com.zestworks.data.network.TMDBService
import com.zestworks.moviedetail.mockdata.DUMMY_ERROR
import com.zestworks.moviedetail.mockdata.dummyResponse
import io.kotlintest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class OfflineFirstRepositoryTest {
    private lateinit var offlineFirstMovieDetailRepository: OfflineFirstMovieDetailRepository

    private val dao: MovieDAO = mockk()
    private val tmdbService: TMDBService = mockk()

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        offlineFirstMovieDetailRepository = OfflineFirstMovieDetailRepository(dao, tmdbService)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Loading from local database when network request fails`() = runBlockingTest {
        coEvery { tmdbService.discoverPopularMovies() }.throws(Exception(DUMMY_ERROR))
        every { dao.getMovieDetail(any()) }.returns(flowOf(dummyResponse))
        val movies = offlineFirstMovieDetailRepository.getMovieDetail(11)
        val toList = movies.toList()
        toList shouldContainExactly listOf(
            Data.Success(dummyResponse)
        )
    }

    @Test
    fun `Throwing error when network request fails and local database is empty`() =
        runBlockingTest {
            coEvery { tmdbService.getMovieDetails(any()) }.throws(Exception(DUMMY_ERROR))
            every { dao.getMovieDetail(any()) }.returns(flowOf(null))
            val movies = offlineFirstMovieDetailRepository.getMovieDetail(11)
            val toList = movies.toList()
            toList shouldContainExactly listOf(
                Data.Error(DUMMY_ERROR)
            )
        }
}