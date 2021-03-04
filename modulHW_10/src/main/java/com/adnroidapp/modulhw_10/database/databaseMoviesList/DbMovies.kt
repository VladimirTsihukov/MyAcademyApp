package com.adnroidapp.modulhw_10.database.databaseMoviesList

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adnroidapp.modulhw_10.database.DatabaseContact
import com.adnroidapp.modulhw_10.database.dao.*
import com.adnroidapp.modulhw_10.database.dbData.DataDBMovies
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesDetails
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesLike

@Database(entities = [DataDBMovies::class,
    DataDBMoviesLike::class,
    DataDBMoviesDetails::class], version = 1)
abstract class DbMovies : RoomDatabase() {

    abstract fun movies(): DaoMovies
    abstract fun moviesLike(): DaoMoviesLike
    abstract fun moviesDetails(): DaoMovieDetails

    companion object {
        private var db: DbMovies? = null
        private val LOCK = Any()

        fun instance(context: Context): DbMovies {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    DbMovies::class.java,
                    DatabaseContact.DATABASE_NAME_MOVIES
                ).fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }
}