package com.zestworks.movielist.di

import com.zestworks.movielist.domain.MovieListRepository
import com.zestworks.movielist.domain.OfflineFirstMovieListRepository
import com.zestworks.movielist.viewmodel.MovieListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val movieListModule = module {
    viewModel { MovieListViewModel(get()) }
    single<MovieListRepository> {
        OfflineFirstMovieListRepository(
            get(),
            get()
        )
    }
}