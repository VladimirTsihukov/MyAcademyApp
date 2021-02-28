package com.adnroidapp.modulhw_10.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesTopRate

@Dao
interface DaoMoviesListTopRate {

    @Query("SELECT * FROM tableMoviesListTopRate ORDER BY ratings DESC")
    fun getTopRateMoviesList(): List<DataDBMoviesTopRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetTopMoviesList(listMovies: List<DataDBMoviesTopRate>)
}