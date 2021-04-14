package com.adnroidapp.modulhw_10.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adnroidapp.modulhw_10.data.dataDb.DataDBMovies

@Dao
interface DaoMovies {

    @Query("SELECT * FROM tableMovies WHERE typeMovie = :moviePopular ORDER BY ratings DESC")
    fun getMoviesCategory(moviePopular: String): List<DataDBMovies>

    @Query("DELETE FROM tableMovies WHERE typeMovie = :moviePopular")
    fun deleteMoviesCategory(moviePopular: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<DataDBMovies>)

    @Query("UPDATE tableMovies SET likeMovie = :likeMovie WHERE id = :id")
    fun setMoviesIdLikeInDb(id : Long, likeMovie : Boolean)
}