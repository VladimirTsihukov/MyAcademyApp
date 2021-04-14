package com.adnroidapp.modulhw_10.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adnroidapp.modulhw_10.data.dataDb.DataDBMoviesLike

@Dao
interface DaoMoviesLike {

    @Query("SELECT * FROM tableMoviesLike ORDER BY ratingsLike")
    fun getMoviesLike(): List<DataDBMoviesLike>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetMoviesLike(moviesLike: DataDBMoviesLike)

    @Query("DELETE FROM tableMoviesLike WHERE idLike = :id")
    fun deleteMoviesLike(id: Long)

    @Query("SELECT idLike FROM tableMoviesLike")
    fun getAllID() : List<Long>
}