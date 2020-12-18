package com.adnroidapp.modulhw_5.data

class ActorsDataSource {
    fun getMoviesList(): List<Actor> {
        return listOf(
            Actor(imageActor = ActorsName.ROBERT_DOWNEY, nameActors = ActorsName.ROBERT_DOWNEY),
            Actor(imageActor = ActorsName.CHRIS_EVANS, nameActors = ActorsName.CHRIS_EVANS),
            Actor(imageActor = ActorsName.MARK_RUFFALO, nameActors = ActorsName.MARK_RUFFALO),
            Actor(imageActor = ActorsName.CHRIS_HAMSWORTH, nameActors = ActorsName.CHRIS_HAMSWORTH),

        )
    }
}