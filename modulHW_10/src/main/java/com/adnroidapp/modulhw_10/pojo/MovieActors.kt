package com.adnroidapp.modulhw_10.pojo

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MovieActors (
    val id: Long,
    val cast: List<Cast>,
)

@Serializable
data class Cast (

    val id: Long,

    val name: String,

    @SerializedName("profile_path")
    val profilePath: String? = null,

)

data class ActorsInfo(
    val nameActor: String,
    val profilePath: String? = null,
)

fun getListActor(listCast: List<Cast>): List<ActorsInfo> {
    val resultList = mutableListOf<ActorsInfo>()
    listCast.forEach {
        resultList.add(ActorsInfo(nameActor = it.name, profilePath = it.profilePath))
    }
    return resultList
}