package com.adnroidapp.modulhw_9.pojo

import com.adnroidapp.modulhw_9.apiCorutine.ApiFactoryCoroutine.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.modulhw_9.database.SealedMovies
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesPopular
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesTopRate
import com.adnroidapp.modulhw_9.ui.data.MovieData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(

    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("genre_ids")
    val genreIDS: List<Long>,

    val id: Long,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String,

    val popularity: Double? = null,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val title: String,

    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Long,

    var likeMovies: Boolean = false,
)

fun Movie.getMovieData(): MovieData {

    return this.run {
        @Suppress("unused")
        (MovieData(
        id = id,
        title = title,
        overview = overview,
        poster = BASE_URL_MOVIE_IMAGE + posterPath,
        backdrop = BASE_URL_MOVIE_IMAGE + backdropPath,
        ratings = voteAverage,
        numberOfRatings = voteCount.toInt(),
        minimumAge = if (adult) 16 else 13,
        likeMovies = likeMovies
    ))
    }
}


fun getMovieAllType(sealed: SealedMovies, list: List<Movie>): List<Any> {
    val listAny = mutableListOf<Any>()
    when(sealed) {
        SealedMovies.MoviesPopular -> {
            list.forEach {
                listAny.add(DataDBMoviesPopular(
                    id = it.id,
                    title = it.title,
                    overview = it.overview,
                    poster = BASE_URL_MOVIE_IMAGE + it.posterPath,
                    backdrop = BASE_URL_MOVIE_IMAGE + it.backdropPath,
                    ratings = it.voteAverage,
                    numberOfRatings = it.voteCount.toInt(),
                    minimumAge = if (it.adult) 16 else 13,
                    likeMovies = it.likeMovies
                ))
            }
            return listAny
        }
        SealedMovies.MoviesTopRate -> {
            list.forEach {
                listAny.add(DataDBMoviesTopRate(
                    id = it.id,
                    title = it.title,
                    overview = it.overview,
                    poster = BASE_URL_MOVIE_IMAGE + it.posterPath,
                    backdrop = BASE_URL_MOVIE_IMAGE + it.backdropPath,
                    ratings = it.voteAverage,
                    numberOfRatings = it.voteCount.toInt(),
                    minimumAge = if (it.adult) 16 else 13,
                    likeMovies = it.likeMovies
                ))
            }
            return listAny
        }
        SealedMovies.MoviesLike -> return listAny
    }
}
