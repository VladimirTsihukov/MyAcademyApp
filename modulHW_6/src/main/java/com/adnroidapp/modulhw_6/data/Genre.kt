package com.adnroidapp.modulhw_6.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
) : Parcelable