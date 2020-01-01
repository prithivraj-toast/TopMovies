package com.zestworks.topmovies

import android.app.Application
import com.zestworks.data.di.dataModule
import com.zestworks.moviedetail.movieDetailModule
import com.zestworks.movielist.movieListModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                listOf(
                    dataModule,
                    movieListModule,
                    movieDetailModule
                )
            )
        }
    }
}