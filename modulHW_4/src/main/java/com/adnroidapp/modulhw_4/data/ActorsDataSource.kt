package com.adnroidapp.modulhw_4.data

class ActorsDataSource {
    fun getMoviesList(): List<Actors> {
        return listOf(
            Actors(imageActor = ActorsName.ROBERT_DOWNEY, nameActors = ActorsName.ROBERT_DOWNEY),
            Actors(imageActor = ActorsName.CHRIS_EVANS, nameActors = ActorsName.CHRIS_EVANS),
            Actors(imageActor = ActorsName.MARK_RUFFALO, nameActors = ActorsName.MARK_RUFFALO),
            Actors(imageActor = ActorsName.CHRIS_HAMSWORTH, nameActors = ActorsName.CHRIS_HAMSWORTH),

        )
    }
}