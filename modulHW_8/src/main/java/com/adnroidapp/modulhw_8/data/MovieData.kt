package com.adnroidapp.modulhw_8.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieData(
    val id: Long,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Double,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Long,
    val genres: String,
) : Parcelable
