package com.adnroidapp.modulhw_8.pojo

import com.adnroidapp.modulhw_8.apiRxJava.ApiFactoryRxJava
import com.adnroidapp.modulhw_8.data.MovieData
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
)

fun Movie.getMovieData(): MovieData {

    return this.run {
        @Suppress("unused")
        (MovieData(
        id = id,
        title = title,
        overview = overview,
        poster = ApiFactoryRxJava.BASE_URL_MOVIE_IMAGE + posterPath,
        backdrop = ApiFactoryRxJava.BASE_URL_MOVIE_IMAGE + backdropPath,
        ratings = voteAverage,
        numberOfRatings = voteCount.toInt(),
        minimumAge = if (adult) 16 else 13,
        runtime = 67,
        genres = "",
    ))
    }
}
