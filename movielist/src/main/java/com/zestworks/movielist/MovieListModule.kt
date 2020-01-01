package com.zestworks.movielist

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val movieListModule = module {
    viewModel { MovieListViewModel(get()) }
    single<MovieListRepository> { OfflineFirstMovieListRepository(get(), get()) }
}