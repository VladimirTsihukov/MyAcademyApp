package com.adnroidapp.modulhw_9.database.dao

import androidx.room.*
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesLike


@Dao
interface DaoMoviesListLike {

    @Query("SELECT * FROM tableMoviesListLike ORDER BY ratings")
    fun getAllMoviesLike(): List<DataDBMoviesLike>

    @Query("SELECT id FROM tableMoviesListLike")
    fun getAllId(): List<Long>

    @Delete
    fun deleteMovieLike(movieLike: DataDBMoviesLike)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieLike(movieLike: DataDBMoviesLike)
}