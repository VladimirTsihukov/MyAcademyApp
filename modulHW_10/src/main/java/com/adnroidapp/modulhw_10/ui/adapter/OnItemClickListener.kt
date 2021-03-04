package com.adnroidapp.modulhw_10.ui.adapter

import com.adnroidapp.modulhw_10.database.dbData.DataDBMovies

interface OnItemClickListener {
    fun onItemClick (id: Long)
    fun onClickLikeMovies(iconLike: Boolean, movie: DataDBMovies)
}