package com.adnroidapp.modulhw_5.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(
    val imageMovie: MovieName,
    val ageCategory: Int,
    val movieGenre: String,
    val star1: Boolean,
    val star2: Boolean,
    val star3: Boolean,
    val star4: Boolean,
    val star5: Boolean,
    val reviews: Int,
    val filName: String,
    val min: Int,
    val iconLike: Boolean,
) : Parcelable

