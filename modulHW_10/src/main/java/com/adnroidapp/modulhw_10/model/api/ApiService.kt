package com.adnroidapp.modulhw_10.model.api

import com.adnroidapp.modulhw_10.data.dataApi.MovieActors
import com.adnroidapp.modulhw_10.data.dataApi.MoviesDetail
import com.adnroidapp.modulhw_10.data.dataApi.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("popular")
    suspend fun getMoviePopularAsync(
    ): Response<MoviesList>

    @GET("top_rated")
    suspend fun getMovieTopRatedAsync(
    ) : Response<MoviesList>

    @GET("{movie_id}")
    suspend fun getMovieByIdAsync(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long,
    ): Response<MoviesDetail>

    @GET("{movie_id}/credits")
    suspend fun getMovieActorsCoroutineAsync(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long,
    ): Response<MovieActors>

    companion object {
        const val QUERY_PARAM_MOVIE_ID_COR = "movie_id"
    }
}
