package com.adnroidapp.modulhw_5.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_5.R
import com.adnroidapp.modulhw_5.adapter.AdapterActors
import com.adnroidapp.modulhw_5.data.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movies_details.*
import kotlinx.android.synthetic.main.fragment_movies_details.view.*
import kotlin.math.roundToInt

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private lateinit var imagePoster: ImageView
    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView
    private lateinit var star4: ImageView
    private lateinit var star5: ImageView
    private lateinit var listStar: List<ImageView>

    private val movie: Movie? by lazy { arguments?.getParcelable(MOVIES_KEY) }
    private val recyclerView: RecyclerView? by lazy {
        view?.findViewById<RecyclerView>(R.id.rec_actors)?.apply {
            adapter = AdapterActors()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagePoster = view.findViewById(R.id.mask)

        star1 = view.findViewById(R.id.mov_list_star_level_1)
        star2 = view.findViewById(R.id.mov_list_star_level_2)
        star3 = view.findViewById(R.id.mov_list_star_level_3)
        star4 = view.findViewById(R.id.mov_list_star_level_4)
        star5 = view.findViewById(R.id.mov_list_star_level_5)
        listStar = listOf(star1, star2, star3, star4, star5)

        movie?.let { getInitLayout(it, view) }
    }

    override fun onStart() {
        super.onStart()
        if (movie?.actors?.isEmpty() == true) {
            cast.visibility = View.INVISIBLE
        } else {
            updateDataActors()
        }
    }

    private fun updateDataActors() {
        (recyclerView?.adapter as? AdapterActors)
            ?.bindActors((movie ?: return).actors)
    }

    @SuppressLint("SetTextI18n")
    fun getInitLayout(movie: Movie, view: View) {
        setPosterIcon(movie.backdrop, view.context)
        view.mov_list_age_category.text = "${movie.minimumAge}+"
        view.mov_list_movie_genre.text = movie.genres.joinToString { it.name }
        view.mov_list_text_story_line.text = movie.overview
        view.mov_list_reviews.text = "${movie.numberOfRatings} Reviews"
        view.mov_list_film_name.text = movie.title
        setImageStars((movie.ratings / 2).roundToInt())

    }

    private fun setPosterIcon(poster: String, context: Context) {
        Glide.with(context)
            .load(poster)
            .into(imagePoster)
    }

    private fun setImageStars(current: Int) {
        for (index in listStar.indices) {
            if (index < current) {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.ic_star_on)
            } else {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.ic_star_off)
            }
        }
    }
}