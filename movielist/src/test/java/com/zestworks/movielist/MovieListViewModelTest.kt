package com.zestworks.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zestworks.common.Data.Success
import com.zestworks.common.LCE
import com.zestworks.common.LCE.Content
import com.zestworks.common.LCE.Error
import com.zestworks.common.LCE.Loading
import com.zestworks.data.model.Movie
import com.zestworks.movielist.mockdata.DUMMY_ERROR
import com.zestworks.movielist.mockdata.errorFlow
import com.zestworks.movielist.mockdata.goodFlow
import com.zestworks.movielist.mockdata.offlineFlow
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
class MovieListViewModelTest {
    private val mockRepository: MovieListRepository = mockk()
    private lateinit var movieListViewModel: MovieListViewModel
    private val observer: Observer<LCE<List<Movie>>> = mockk(relaxed = true)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `loading data - happy path`() = runBlockingTest {
        every { mockRepository.getMovies() }.returns(goodFlow)
        movieListViewModel = MovieListViewModel(mockRepository)
        movieListViewModel.viewState.observeForever(observer)
        val dummyItems = goodFlow.toList()
        val success = dummyItems.first() as Success
        observer.apply {
            verifySequence {
                onChanged(Loading)
                onChanged(Content(success.data))
            }
        }
    }

    @Test
    fun `loading data - network request fails`() = runBlockingTest {
        every { mockRepository.getMovies() }.returns(errorFlow)
        movieListViewModel = MovieListViewModel(mockRepository)
        movieListViewModel.viewState.observeForever(observer)
        observer.apply {
            verifySequence {
                onChanged(Loading)
                onChanged(Error(DUMMY_ERROR))
            }
        }
    }

    @Test
    fun `loading data - network request fails after loading offline data`() = runBlockingTest {
        every { mockRepository.getMovies() }.returns(offlineFlow)
        movieListViewModel = MovieListViewModel(mockRepository)
        movieListViewModel.viewState.observeForever(observer)
        val dummyItems = offlineFlow.toList()
        val success = dummyItems.first() as Success
        observer.apply {
            verifySequence {
                onChanged(Loading)
                onChanged(Content(success.data))
                onChanged(Error(DUMMY_ERROR))
            }
        }
    }
}
