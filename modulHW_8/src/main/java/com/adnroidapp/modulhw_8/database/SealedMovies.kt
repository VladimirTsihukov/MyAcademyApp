package com.adnroidapp.modulhw_8.database

sealed class SealedMovies {
    object MoviesPopular : SealedMovies()
    object MoviesTopRate : SealedMovies()
    object MoviesLike : SealedMovies()
}