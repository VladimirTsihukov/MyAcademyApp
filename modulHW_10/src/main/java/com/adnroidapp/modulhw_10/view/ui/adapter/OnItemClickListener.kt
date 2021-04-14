package com.adnroidapp.modulhw_10.view.ui.adapter

import com.adnroidapp.modulhw_10.data.dataDb.DataDBMovies

interface OnItemClickListener {
    fun onItemClick (id: Long)
    fun onClickLikeMovies(iconLike: Boolean, movie: DataDBMovies)
}