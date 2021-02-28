package com.adnroidapp.modulhw_10.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesList (
    val results: List<Movie>,
): Parcelable
