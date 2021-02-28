package com.adnroidapp.modulhw_10.database.dbData

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adnroidapp.modulhw_10.database.DatabaseContact
import kotlinx.android.parcel.Parcelize

@Entity(tableName = DatabaseContact.TABLE_NAME_MOVIES_DETAILS)
@Parcelize
data class DataDBMoviesDetails (
    @PrimaryKey
    val id: Long,

    val title: String,
    val overview: String,
    val backdrop: String,
    val ratings: Double,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Long = -1,
    val genres: String = "",
    val nameActors: String = "",
    val profilePaths: String ="",
    val likeMovies: Boolean = false
): Parcelable