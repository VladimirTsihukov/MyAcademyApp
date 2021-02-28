package com.adnroidapp.modulhw_10.database.databaseMoviesList

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adnroidapp.modulhw_10.database.DatabaseContact
import com.adnroidapp.modulhw_10.database.dao.DaoMovieDetails
import com.adnroidapp.modulhw_10.database.dao.DaoMoviesListLike
import com.adnroidapp.modulhw_10.database.dao.DaoMoviesListPopular
import com.adnroidapp.modulhw_10.database.dao.DaoMoviesListTopRate
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesDetails
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesLike
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesPopular
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesTopRate

@Database(entities = [DataDBMoviesLike::class,
    DataDBMoviesPopular::class,
    DataDBMoviesTopRate::class,
    DataDBMoviesDetails::class], version = 1)
abstract class DbMovies : RoomDatabase() {

    abstract fun moviesDetails(): DaoMovieDetails
    abstract fun moviesPopularDao(): DaoMoviesListPopular
    abstract fun moviesTopRateDao(): DaoMoviesListTopRate
    abstract fun moviesListLike(): DaoMoviesListLike

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