package com.adnroidapp.modulhw_9.ui.adapter

import com.adnroidapp.modulhw_9.ui.data.MovieData


interface OnItemClickListener {
    fun onItemClick (id: Long)
    fun onClickLikeMovies(movie: MovieData)
    fun deleteLikeMovies(movie: MovieData)
}