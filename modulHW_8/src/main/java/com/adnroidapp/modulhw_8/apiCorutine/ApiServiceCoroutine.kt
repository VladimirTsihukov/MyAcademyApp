package com.adnroidapp.modulhw_8.apiCorutine

import com.adnroidapp.modulhw_8.pojo.InformationMovies
import com.adnroidapp.modulhw_8.pojo.MovieActors
import com.adnroidapp.modulhw_8.pojo.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceCoroutine {

    @GET("popular")
    suspend fun getPopularMoviesAsync(
    ): Response<MoviesList>

    @GET("top_rated")
    suspend fun getMovieTopRatedAsync(
    ) : Response<MoviesList>

    @GET("{movie_id}")
    suspend fun getMovieByIdAsync(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long,
    ): Response<InformationMovies>

    @GET("{movie_id}/credits")
    suspend fun getMovieActorsCoroutineAsync(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long,
    ): Response<MovieActors>

    companion object {
        const val QUERY_PARAM_MOVIE_ID_COR = "movie_id"
    }
}
