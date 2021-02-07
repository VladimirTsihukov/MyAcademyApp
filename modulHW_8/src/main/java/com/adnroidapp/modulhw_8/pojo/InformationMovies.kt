package com.adnroidapp.modulhw_8.pojo

import com.adnroidapp.modulhw_8.apiRxJava.ApiFactoryRxJava
import com.adnroidapp.modulhw_8.database.dbData.DataDBMoviesDetails
import com.adnroidapp.modulhw_8.ui.data.MovieData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class InformationMovies(
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,

    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,

    @SerializedName("imdb_id")
    val imdbID: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,

    @SerializedName("release_date")
    val releaseDate: String,

    val revenue: Long,
    val runtime: Long,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,

    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Long,
)

fun InformationMovies.getMovieDataInfo(): MovieData {

    return this.run {
        @Suppress("unused")
        (MovieData(
        id = id,
        title = title,
        overview = overview,
        poster = if (belongsToCollection != null) ApiFactoryRxJava.BASE_URL_MOVIE_IMAGE + belongsToCollection.posterPath
        else "https://image.tmdb.org/t/p/w500//LdSn17U6ybhtPJT3S6fTNRni5Y.jpg",
        backdrop = ApiFactoryRxJava.BASE_URL_MOVIE_IMAGE + backdropPath,
        ratings = voteAverage,
        numberOfRatings = voteCount.toInt(),
        minimumAge = if (adult) 16 else 13,
        runtime = runtime,
        genres = genres.joinToString(", ") { it.name },
    ))
    }
}

fun InformationMovies.getMovieDetails(): DataDBMoviesDetails {

    return this.run {
        @Suppress("unused")
        (DataDBMoviesDetails(
            id = id,
            title = title,
            overview = overview,
            poster = if (belongsToCollection != null) ApiFactoryRxJava.BASE_URL_MOVIE_IMAGE + belongsToCollection.posterPath
            else "https://image.tmdb.org/t/p/w500//LdSn17U6ybhtPJT3S6fTNRni5Y.jpg",
            backdrop = ApiFactoryRxJava.BASE_URL_MOVIE_IMAGE + backdropPath,
            ratings = voteAverage,
            numberOfRatings = voteCount.toInt(),
            minimumAge = if (adult) 16 else 13,
            runtime = runtime,
            genres = genres.joinToString(", ") { it.name },
        ))
    }
}

@Serializable
data class BelongsToCollection(
    val id: Long,
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
)

@Serializable
data class Genre(
    val id: Long,
    val name: String,
)

@Serializable
data class ProductionCompany(
    val id: Long,

    @SerializedName("logo_path")
    val logoPath: String,

    val name: String,

    @SerializedName("origin_country")
    val originCountry: String,
)

@Serializable
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso3166_1: String,

    val name: String,
)

@Serializable
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("iso_639_1")
    val iso639_1: String,

    val name: String,
)
