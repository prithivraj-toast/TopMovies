package com.zestworks.data.di

import com.google.gson.Gson
import com.zestworks.data.db.AppDb
import com.zestworks.data.network.TMDBService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(TMDBService::class.java)
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor {
                val newUrl =
                    it.request().url().newBuilder()
                        .addQueryParameter("api_key", "3769f0f034b3bf87f8d911470442ab76")
                val newRequest = it.request().newBuilder().url(newUrl.build()).build()
                it.proceed(newRequest)
            }
            .build()
    }

    single {
        AppDb.getDatabase(get()).movieDao()
    }
}