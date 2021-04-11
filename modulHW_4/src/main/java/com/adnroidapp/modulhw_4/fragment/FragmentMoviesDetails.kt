package com.adnroidapp.modulhw_4.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_4.R
import com.adnroidapp.modulhw_4.adapter.AdapterActors
import com.adnroidapp.modulhw_4.data.ActorsDataSource
import com.adnroidapp.modulhw_4.data.Movies
import kotlinx.android.synthetic.main.fragment_movies_details.view.*

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private val movie: Movies? by lazy { arguments?.getParcelable(MOVIES_KEY) }
    private val recyclerView: RecyclerView? by lazy { view?.findViewById<RecyclerView>(R.id.rec_actors)?.apply {
        adapter = AdapterActors()
    } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie?.let { getInitLayout(it, view) }
    }

    override fun onStart() {
        super.onStart()
        updateDataActors()
    }

    private fun updateDataActors() {
        (recyclerView?.adapter as? AdapterActors)?.run {
            bindActors(ActorsDataSource().getMoviesList())
        }
    }

    @SuppressLint("SetTextI18n")
    fun getInitLayout(movie: Movies, view: View) {
        view.mov_list_age_category.text = "${movie.ageCategory}+"
        view.mov_list_movie_genre.text = movie.movieGenre
        if (movie.star1) view.mov_list_star_level_1.setImageResource(R.drawable.ic_star_on)
        if (movie.star2) view.mov_list_star_level_2.setImageResource(R.drawable.ic_star_on)
        if (movie.star3) view.mov_list_star_level_3.setImageResource(R.drawable.ic_star_on)
        if (movie.star4) view.mov_list_star_level_4.setImageResource(R.drawable.ic_star_on)
        if (movie.star5) view.mov_list_star_level_5.setImageResource(R.drawable.ic_star_on)
        view.mov_list_reviews.text = "${movie.reviews} Reviews"
        view.mov_list_film_name.text = movie.filName
    }
}