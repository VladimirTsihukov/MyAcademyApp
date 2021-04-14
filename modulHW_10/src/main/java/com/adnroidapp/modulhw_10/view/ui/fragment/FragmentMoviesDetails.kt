package com.adnroidapp.modulhw_10.view.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.data.dataApi.ActorsInfo
import com.adnroidapp.modulhw_10.data.dataDb.DataDBMoviesDetails
import com.adnroidapp.modulhw_10.view.ui.adapter.AdapterActors
import com.adnroidapp.modulhw_10.view.ui.viewModel.ViewModelMovieDetails
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movies_details.*
import kotlinx.android.synthetic.main.fragment_movies_details.view.*
import kotlinx.android.synthetic.main.layout_back_fragment.view.*
import kotlinx.android.synthetic.main.layout_stars.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private val mViewModelModelDetails: ViewModelMovieDetails by viewModels()

    private lateinit var recyclerView: RecyclerView
    private val adapter = AdapterActors()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rec_view_actors)
        recyclerView.adapter = adapter

        arguments?.getLong(MOVIES_KEY)?.let {
            mViewModelModelDetails.loadMovieIdDetails(it)
        }

        initView(view)

        initObserver(view)
    }

    private fun initView(view: View) {
        view.tv_back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver(view: View) {
        mViewModelModelDetails.liveDataMoviesDetails.observe(viewLifecycleOwner,
            { movieDetail ->
                movieDetail?.run {
                    getInitLayout(movieDetail)
                    setVisibilityDataLoader()
                }
            })

        mViewModelModelDetails.liveDataMovieActors.observe(viewLifecycleOwner,
            { actors ->
                actors?.let {
                    if (it.isEmpty()) {
                        tv_mov_list_cast.visibility = View.INVISIBLE
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
        adapter.actors = movieActors
    }

    private fun getInitLayout(movieData: DataDBMoviesDetails) {
        view?.also {
            tv_mov_list_age_category.text = resources.getString(R.string.fragment_age_category).let {
                String.format(it, "${movieData.minimumAge}")
            }
            tv_mov_list_movie_genre.text = movieData.genres
            tv_mov_list_text_story_line.text = movieData.overview
            tv_mov_list_reviews.text = resources.getString(R.string.fragment_reviews).let {
                String.format(it, "${movieData.numberOfRatings}")
            }
            tv_mov_list_film_name.text = movieData.title
            setPosterIcon(movieData.backdrop)
            setImageStars((movieData.ratings / 2).roundToInt())
        }
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

    private fun setPosterIcon(poster: String) {
        view?.apply {
            Glide.with(context)
                .load(poster)
                .error(R.drawable.ic_ph_movie_grey_400)
                .into(img_poster)
        }
    }

    private fun setImageStars(current: Int) {
        view?.apply {
            val listStar = listOf<ImageView>(
                img_mov_list_star_level_1,
                img_mov_list_star_level_2,
                img_mov_list_star_level_3,
                img_mov_list_star_level_4,
                img_mov_list_star_level_5)

            listStar.forEachIndexed { index, _ ->
                if (index < current) {
                    listStar[index].setImageResource(R.drawable.ic_star_on)
                } else {
                    listStar[index].setImageResource(R.drawable.ic_star_off)
                }
            }
        }
    }
}