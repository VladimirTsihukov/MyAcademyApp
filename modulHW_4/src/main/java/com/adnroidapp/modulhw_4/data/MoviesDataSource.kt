package com.adnroidapp.modulhw_4.data

class MoviesDataSource {
    fun getMoviesList(): List<Movies> {
        return listOf(
            Movies(imageFilm = FilmName.AVENGERS_END_GAME, ageCategory = 13, movieGenre = "Action, Adventure, Drama",
                star1 = true, star2 = true, star3 = true, star4 = true, star5 = false, reviews = 125,
                filName = "Avengers: End Game", min = 137, iconLike = false),
            Movies(imageFilm = FilmName.TENET, ageCategory = 16, movieGenre = "Action, Sci-Fi, Thriller",
                star1 = true, star2 = true, star3 = true, star4 = true, star5 = true, reviews = 98,
                filName = "Tenet", min = 97, iconLike = true),
            Movies(imageFilm = FilmName.BLACK_WINDOW, ageCategory = 13, movieGenre = "Action, Adventure, Sci-Fi",
                star1 = true, star2 = true, star3 = true, star4 = true, star5 = false, reviews = 38,
                filName = "Black Widow", min = 102, iconLike = false),
            Movies(imageFilm = FilmName.WONDER_WOMAN_1984, ageCategory = 13, movieGenre = "Action, Adventure, Fantasy",
                star1 = true, star2 = true, star3 = true, star4 = true, star5 = true, reviews = 74,
                filName = "Wonder Woman 1984", min = 120, iconLike = false),
        )
    }
}