package com.zestworks.moviedetail.di

import com.zestworks.moviedetail.domain.MovieDetailRepository
import com.zestworks.moviedetail.domain.OfflineFirstMovieDetailRepository
import com.zestworks.moviedetail.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val movieDetailModule = module {
    viewModel { MovieDetailViewModel(get()) }
    single<MovieDetailRepository> {
        OfflineFirstMovieDetailRepository(
            get(),
            get()
        )
    }
}