package com.adnroidapp.modulhw_8.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_8.R
import com.adnroidapp.modulhw_8.pojo.ActorsInfo
import com.adnroidapp.modulhw_8.ui.adapter.AdapterActors
import com.adnroidapp.modulhw_8.ui.data.MovieData
import com.adnroidapp.modulhw_8.ui.viewModelCoroutine.ViewModelMovieDetailsCoroutine
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

    private val mViewModelModelDetailsCoroutine: ViewModelMovieDetailsCoroutine by viewModels()

    private val recyclerView: RecyclerView? by lazy {
        view?.findViewById<RecyclerView>(R.id.rec_actors)?.apply {
            adapter = AdapterActors()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLong(MOVIES_KEY)?.let {
            mViewModelModelDetailsCoroutine.initMovieIdDetails(it)
        }

        initView(view)

        initObserver(view)
    }

    private fun initObserver(view: View) {
        mViewModelModelDetailsCoroutine.liveDataMoviesDetailsCoroutine.observe(viewLifecycleOwner,
            { movieDetail ->
                movieDetail?.run {
                    getInitLayout(movieDetail, view)
                }
            })

        mViewModelModelDetailsCoroutine.liveDataMovieActorsCoroutine.observe(viewLifecycleOwner,
            { actors ->
                actors?.let {
                    if (it.isEmpty()) {
                        cast.visibility = View.INVISIBLE
                    } else {
                        updateDataActors(it)
                    }
                }
            })

        mViewModelModelDetailsCoroutine.liveDataErrorServerApi.observe(viewLifecycleOwner, {Error ->
            val snackBar = Snackbar.make(view, Error, Snackbar.LENGTH_LONG)
            snackBar.show()
        })
    }

    private fun initView(view: View) {
        imagePoster = view.findViewById(R.id.mask)

        star1 = view.findViewById(R.id.mov_list_star_level_1)
        star2 = view.findViewById(R.id.mov_list_star_level_2)
        star3 = view.findViewById(R.id.mov_list_star_level_3)
        star4 = view.findViewById(R.id.mov_list_star_level_4)
        star5 = view.findViewById(R.id.mov_list_star_level_5)
        listStar = listOf(star1, star2, star3, star4, star5)
    }

    private fun updateDataActors(movieActors: List<ActorsInfo>) {
        (recyclerView?.adapter as? AdapterActors)
            ?.bindActors(movieActors)
    }

    @SuppressLint("SetTextI18n")
    fun getInitLayout(movieData: MovieData, view: View) {
        setPosterIcon(movieData.backdrop, view.context)
        view.mov_list_age_category.text = "${movieData.minimumAge}+"
        view.mov_list_movie_genre.text = movieData.genres
        view.mov_list_text_story_line.text = movieData.overview
        view.mov_list_reviews.text = "${movieData.numberOfRatings} Reviews"
        view.mov_list_film_name.text = movieData.title
        setImageStars((movieData.ratings / 2).roundToInt())

        view.dataLoader.visibility = View.INVISIBLE
    }

    private fun setPosterIcon(poster: String, context: Context) {
        Glide.with(context)
            .load(poster)
            .into(imagePoster)
    }

    private fun setImageStars(current: Int) {

        listStar.forEachIndexed { index, _ ->
            if (index < current) {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.ic_star_on)
            } else {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.ic_star_off)
            }
        }
    }
}