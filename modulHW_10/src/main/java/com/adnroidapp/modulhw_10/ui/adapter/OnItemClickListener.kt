package com.adnroidapp.modulhw_10.ui.adapter

import com.adnroidapp.modulhw_10.ui.data.MovieData


interface OnItemClickListener {
    fun onItemClick (id: Long)
    fun onClickLikeMovies(movie: MovieData)
    fun deleteLikeMovies(movie: MovieData)
}