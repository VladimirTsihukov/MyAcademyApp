package com.adnroidapp.modulhw_8.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adnroidapp.modulhw_8.database.dbData.DataDBMoviesPopular

@Dao
interface DaoMoviesListPopular {

    @Query("SELECT * FROM tableMoviesListPopular ORDER BY ratings DESC")
    fun getPopularMoviesList(): List<DataDBMoviesPopular>

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //обновляем список
    fun insertPopularMoviesList(listMovies: List<DataDBMoviesPopular>)
}