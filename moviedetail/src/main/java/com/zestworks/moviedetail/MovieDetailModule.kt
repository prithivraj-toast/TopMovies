package com.zestworks.moviedetail

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val movieDetailModule = module {
    viewModel { MovieDetailViewModel(get()) }
    single<MovieDetailRepository> { OfflineFirstMovieDetailRepository(get(), get()) }
}