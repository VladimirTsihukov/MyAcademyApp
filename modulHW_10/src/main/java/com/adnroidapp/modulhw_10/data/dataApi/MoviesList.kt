package com.adnroidapp.modulhw_10.data.dataApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesList (
    val results: List<Movie>,
): Parcelable
