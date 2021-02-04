package com.adnroidapp.modulhw_8.ui.adapter

import com.adnroidapp.modulhw_8.ui.data.MovieData


interface OnItemClickListener {
    fun onItemClick (id: Long)
    fun onClickLikeMovies(movie: MovieData)
    fun deleteLikeMovies(movie: MovieData)
}