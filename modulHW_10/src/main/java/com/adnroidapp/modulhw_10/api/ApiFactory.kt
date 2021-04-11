package com.adnroidapp.modulhw_10.api

import com.adnroidapp.modulhw_10.App
import com.adnroidapp.modulhw_10.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val BASE_URL_MOVIE_COR = "https://api.themoviedb.org/3/movie/"
    private const val API_KEY_ID_COR = "7d9db2e12493542315f5bcb0f3f0de61"
    private const val API_KEY_COR = "api_key"
    private const val QUERY_PARAM_LANGUAGE_COR = "language"
    private val LANGUAGE_COR = App.instance.getString(R.string.LANGUAGE_API)
    const val BASE_URL_MOVIE_IMAGE = "https://image.tmdb.org/t/p/w500/"

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter(API_KEY_COR, API_KEY_ID_COR)
            .addQueryParameter(QUERY_PARAM_LANGUAGE_COR, LANGUAGE_COR)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val tmdClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    private fun retrofitMovieCor(): Retrofit = Retrofit.Builder()
        .client(tmdClient)
        .baseUrl(BASE_URL_MOVIE_COR)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val API_SERVICE_MOVIE: ApiService =
        retrofitMovieCor().create(ApiService::class.java)
}