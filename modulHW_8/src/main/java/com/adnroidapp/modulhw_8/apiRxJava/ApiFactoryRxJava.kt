package com.adnroidapp.modulhw_8.apiRxJava

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactoryRxJava {

    private const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/"
    const val BASE_URL_MOVIE_IMAGE = "https://image.tmdb.org/t/p/w500/"

    private val retrofitMovie = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL_MOVIE)
        .build()

    val apiServiceMoviesRxJava: ApiServiceRxJava = retrofitMovie.create(ApiServiceRxJava::class.java)
}