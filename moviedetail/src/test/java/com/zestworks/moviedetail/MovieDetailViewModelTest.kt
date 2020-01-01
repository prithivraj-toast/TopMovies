package com.zestworks.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zestworks.common.Data
import com.zestworks.common.LCE
import com.zestworks.data.model.MovieDetail
import com.zestworks.moviedetail.mockdata.DUMMY_ERROR
import com.zestworks.moviedetail.mockdata.errorFlow
import com.zestworks.moviedetail.mockdata.goodFlow
import com.zestworks.moviedetail.mockdata.offlineFlow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {
    private val mockRepository: MovieDetailRepository = mockk()
    private val observer: Observer<LCE<MovieDetail>> = mockk(relaxed = true)

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `loading movie details - happy path`() = runBlockingTest {
        every { mockRepository.getMovieDetail(any()) }.returns(goodFlow)
        val movieDetailViewModel = MovieDetailViewModel(mockRepository)
        movieDetailViewModel.viewState(11).observeForever(observer)
        val dummyItems = goodFlow.toList()
        val success = dummyItems.first() as Data.Success
        observer.apply {
            verifySequence {
                onChanged(LCE.Loading)
                onChanged(LCE.Content(success.data))
            }
        }
    }

    @Test
    fun `loading data - network request fails`() = runBlockingTest {
        every { mockRepository.getMovieDetail(any()) }.returns(errorFlow)
        val movieListViewModel = MovieDetailViewModel(mockRepository)
        movieListViewModel.viewState(11).observeForever(observer)
        observer.apply {
            verifySequence {
                onChanged(LCE.Loading)
                onChanged(LCE.Error(DUMMY_ERROR))
            }
        }
    }

    @Test
    fun `loading data - network request fails after loading offline data`() = runBlockingTest {
        every { mockRepository.getMovieDetail(any()) }.returns(offlineFlow)
        val movieListViewModel = MovieDetailViewModel(mockRepository)
        movieListViewModel.viewState(11).observeForever(observer)
        val dummyItems = offlineFlow.toList()
        val success = dummyItems.first() as Data.Success
        observer.apply {
            verifySequence {
                onChanged(LCE.Loading)
                onChanged(LCE.Content(success.data))
                onChanged(LCE.Error(DUMMY_ERROR))
            }
        }
    }
}
