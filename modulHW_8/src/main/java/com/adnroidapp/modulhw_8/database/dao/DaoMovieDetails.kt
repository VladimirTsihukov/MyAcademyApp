package com.adnroidapp.modulhw_8.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adnroidapp.modulhw_8.database.dbData.DataDBMoviesDetails

@Dao
interface DaoMovieDetails {

    @Query("SELECT * FROM tableMoviesDetails ORDER BY id")
    fun getAllMoviesDetails(): List<DataDBMoviesDetails>

    @Query("SELECT * FROM tableMoviesDetails WHERE id = :idMovie LIMIT 1")
    fun getMovieDetail(idMovie: Long): DataDBMoviesDetails

    @Query("UPDATE tableMoviesDetails SET nameActors = :name WHERE id = :id")
    fun setNameActors(name: String, id: Long)

    @Query("UPDATE tableMoviesDetails SET profilePaths = :profilePaths WHERE id = :id")
    fun setProfilePaths(profilePaths: String, id: Long)

    @Query("SELECT nameActors FROM tableMoviesDetails WHERE id=:id")
    fun getNameActors(id: Long): String

    @Query("SELECT profilePaths FROM tableMoviesDetails WHERE id=:id")
    fun getProfilePaths(id: Long): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieDetail: DataDBMoviesDetails)
}