package com.adnroidapp.modulhw_8.pojo

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MovieActors (
    val id: Long,
    val cast: List<Cast>,
)

@Serializable
data class Cast (
    val adult: Boolean,
    val gender: Long,
    val id: Long,

    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("cast_id")
    val castID: Long? = null,

    val character: String? = null,

    @SerializedName("credit_id")
    val creditID: String,

    val order: Long? = null,
    val department: String? = null,
    val job: String? = null
)
