package com.adnroidapp.modulhw_10.pojo

import android.os.Parcelable
import com.adnroidapp.modulhw_10.apiCorutine.ApiFactoryCoroutine.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.modulhw_10.database.SealedMovies
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesPopular
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesTopRate
import com.adnroidapp.modulhw_10.ui.data.MovieData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(

    val id: Long,

    val title: String,

    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Long,

    val adult: Boolean,

    var likeMovies: Boolean = false,
) : Parcelable

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
