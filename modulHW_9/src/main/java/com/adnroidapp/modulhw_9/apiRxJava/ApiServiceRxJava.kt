package com.adnroidapp.modulhw_9.apiRxJava

import com.adnroidapp.modulhw_9.pojo.InformationMovies
import com.adnroidapp.modulhw_9.pojo.MovieActors
import com.adnroidapp.modulhw_9.pojo.MoviesList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceRxJava {

    @GET("popular")
    fun getMoviesPopular(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = LANGUAGE_RUS,
        @Query(QUERY_PARAM_PAGE) page: Int = PAGE
    ) : Single<MoviesList>

    @GET("top_rated")
    fun getMoviesTopRated(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = LANGUAGE_RUS,
        @Query(QUERY_PARAM_PAGE) page: Int = PAGE
    ) : Single<MoviesList>

    @GET("{movie_id}")
    fun getMovieInformation(
        @Path(QUERY_PARAM_MOVIE_ID) movieID: Long,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = LANGUAGE_RUS
    ) : Single<InformationMovies>

    @GET("{movie_id}/credits")
    fun getMovieActors(
        @Path(QUERY_PARAM_MOVIE_ID) movieID: Long,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = LANGUAGE_RUS
    ) : Single<MovieActors>

    companion object {
         const val API_KEY = "7d9db2e12493542315f5bcb0f3f0de61"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_MOVIE_ID = "movie_id"
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val LANGUAGE_US = "en-US"
        private const val LANGUAGE_RUS = "ru"
        private const val QUERY_PARAM_PAGE = "page"
        private const val PAGE = 1
        const val TAG_LOAD = "TEST_OF_LOADING"
    }
}