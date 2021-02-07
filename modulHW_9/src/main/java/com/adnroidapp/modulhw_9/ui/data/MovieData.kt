package com.adnroidapp.modulhw_9.ui.data

import android.os.Parcelable
import com.adnroidapp.modulhw_9.database.SealedMovies
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesDetails
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesLike
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesPopular
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesTopRate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieData(

    val id: Long,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Double,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Long = -1,
    val genres: String = "",
    val nameActors: String = "",
    val profilePaths: String = "",
    var likeMovies: Boolean = false
) : Parcelable

fun MovieData.getMovieLike(): DataDBMoviesLike {
    return DataDBMoviesLike(
        id = id,
        title = title,
        overview = overview,
        poster = poster,
        backdrop = backdrop,
        ratings = ratings,
        numberOfRatings = numberOfRatings,
        minimumAge = minimumAge,
        likeMovies = likeMovies
    )
}

fun getMovieData(movieDb: DataDBMoviesDetails): MovieData {
    return MovieData(
        id = movieDb.id,
        title = movieDb.title,
        overview = movieDb.overview,
        poster = movieDb.poster,
        backdrop = movieDb.backdrop,
        ratings = movieDb.ratings,
        numberOfRatings = movieDb.numberOfRatings,
        minimumAge = movieDb.minimumAge,
        runtime = movieDb.runtime,
        genres = movieDb.genres,
        likeMovies = movieDb.likeMovies
    )
}

fun getListMovieData(sealedMovies: SealedMovies, listAny: List<Any>): List<MovieData> {
    val listMovieData = mutableListOf<MovieData>()

    when (sealedMovies) {
        SealedMovies.MoviesPopular -> {
            (listAny as List<DataDBMoviesPopular>).let {
                listAny.forEach {
                    listMovieData.add(MovieData(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        poster = it.poster,
                        backdrop = it.backdrop,
                        ratings = it.ratings,
                        numberOfRatings = it.numberOfRatings,
                        minimumAge = it.minimumAge,
                        likeMovies = it.likeMovies
                    ))
                }
                return listMovieData
            }
        }
        SealedMovies.MoviesTopRate -> {
            (listAny as List<DataDBMoviesTopRate>).let {
                listAny.forEach {
                    listMovieData.add(MovieData(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        poster = it.poster,
                        backdrop = it.backdrop,
                        ratings = it.ratings,
                        numberOfRatings = it.numberOfRatings,
                        minimumAge = it.minimumAge,
                        likeMovies = it.likeMovies
                    ))
                }
                return listMovieData
            }
        }
        SealedMovies.MoviesLike -> {
            (listAny as List<DataDBMoviesLike>).let {
                listAny.forEach {
                    listMovieData.add(MovieData(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        poster = it.poster,
                        backdrop = it.backdrop,
                        ratings = it.ratings,
                        numberOfRatings = it.numberOfRatings,
                        minimumAge = it.minimumAge,
                        likeMovies = it.likeMovies
                    ))
                }
                return listMovieData
            }
        }
    }
}