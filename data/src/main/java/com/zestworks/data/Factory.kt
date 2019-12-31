package com.zestworks.data

import android.content.Context
import com.google.gson.Gson
import com.zestworks.data.db.AppDb
import com.zestworks.data.db.MovieDAO
import com.zestworks.data.network.TMDBService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//TODO Change to DI
fun getMoviesDAO(context: Context): MovieDAO = AppDb.getDatabase(context).movieDao()

fun getTmdbService(): TMDBService = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org")
    .client(getOkHttp())
    .addConverterFactory(GsonConverterFactory.create(Gson()))
    .build()
    .create(TMDBService::class.java)

private fun getOkHttp() = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor())
    .addInterceptor {

        val newUrl =
            it.request().url().newBuilder()
                .addQueryParameter("api_key", getAPIKey())
        val newRequest = it.request().newBuilder().url(newUrl.build()).build()
        it.proceed(newRequest)
    }
    .build()

private fun getAPIKey() = "3769f0f034b3bf87f8d911470442ab76"