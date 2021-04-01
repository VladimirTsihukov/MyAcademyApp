package com.adnroidapp.modulhw_10.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesDetails
import com.adnroidapp.modulhw_10.pojo.ActorsInfo
import com.adnroidapp.modulhw_10.ui.adapter.AdapterActors
import com.adnroidapp.modulhw_10.ui.viewModelCoroutine.ViewModelMovieDetails
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movies_details.*
import kotlinx.android.synthetic.main.fragment_movies_details.view.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    private lateinit var imagePoster: ImageView
    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView
    private lateinit var star4: ImageView
    private lateinit var star5: ImageView
    private lateinit var listStar: List<ImageView>
    private lateinit var textBack: TextView

    private val mViewModelModelDetails: ViewModelMovieDetails by viewModels()

    private lateinit var recyclerView: RecyclerView;
    private val adapter by lazy { AdapterActors() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rec_actors)
        recyclerView.adapter = adapter

        arguments?.getLong(MOVIES_KEY)?.let {
            mViewModelModelDetails.loadMovieIdDetails(it)
        }

        initView(view)

        initObserver(view)
    }

    private fun initView(view: View) {
        imagePoster = view.findViewById(R.id.mask)

        star1 = view.findViewById(R.id.mov_list_star_level_1)
        star2 = view.findViewById(R.id.mov_list_star_level_2)
        star3 = view.findViewById(R.id.mov_list_star_level_3)
        star4 = view.findViewById(R.id.mov_list_star_level_4)
        star5 = view.findViewById(R.id.mov_list_star_level_5)
        listStar = listOf(star1, star2, star3, star4, star5)
        textBack = view.findViewById(R.id.text_back)

        textBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMoviesDetails_to_fragmentMoviesList)
        }
    }

    private fun initObserver(view: View) {
        mViewModelModelDetails.liveDataMoviesDetails.observe(viewLifecycleOwner,
            { movieDetail ->
                movieDetail?.run {
                    getInitLayout(movieDetail, view)
                    setVisibilityDataLoader()
                }
            })

        mViewModelModelDetails.liveDataMovieActors.observe(viewLifecycleOwner,
            { actors ->
                actors?.let {
                    if (it.isEmpty()) {
                        cast.visibility = View.INVISIBLE
                    } else {
                        updateDataActors(it)
                    }
                }
            })

        mViewModelModelDetails.liveDataErrorServerApi.observe(viewLifecycleOwner,
            { Error ->
                val snackBar = Snackbar.make(view, Error, Snackbar.LENGTH_LONG)
                snackBar.show()
            })
    }

    private fun updateDataActors(movieActors: List<ActorsInfo>) {
        adapter.bindActors(movieActors)
    }

    private fun getInitLayout(movieData: DataDBMoviesDetails, view: View) {
        view.mov_list_age_category.text = resources.getString(R.string.fragment_reviews).let {
            String.format(it, "${movieData.minimumAge}")
        }
        view.mov_list_movie_genre.text = movieData.genres
        view.mov_list_text_story_line.text = movieData.overview
        view.mov_list_reviews.text = resources.getString(R.string.fragment_reviews).let {
            String.format(it, "${movieData.numberOfRatings}")
        }
        view.mov_list_film_name.text = movieData.title
        setPosterIcon(movieData.backdrop, view.context)
        setImageStars((movieData.ratings / 2).roundToInt())
    }

    @SuppressLint("CheckResult")
    private fun setVisibilityDataLoader() {
        Observable.timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            view?.let {
                data_loader.visibility = View.INVISIBLE
            }
        }
    }

    private fun setPosterIcon(poster: String, context: Context) {
        Glide.with(context)
            .load(poster)
            .error(R.drawable.ph_movie_grey_400)
            .into(imagePoster)
    }

    private fun setImageStars(current: Int) {

        listStar.forEachIndexed { index, _ ->
            if (index < current) {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.star_icon_on)
            } else {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.star_icon_off)
            }
        }
    }
}