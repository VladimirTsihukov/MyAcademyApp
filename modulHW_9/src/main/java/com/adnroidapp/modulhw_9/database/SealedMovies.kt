package com.adnroidapp.modulhw_9.database

sealed class SealedMovies {
    object MoviesPopular : SealedMovies()
    object MoviesTopRate : SealedMovies()
    object MoviesLike : SealedMovies()
}