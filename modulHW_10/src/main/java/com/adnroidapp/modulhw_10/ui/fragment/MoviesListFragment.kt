package com.adnroidapp.modulhw_10.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_10.App
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.database.dbData.DataDBMovies
import com.adnroidapp.modulhw_10.ui.EnumTypeMovie
import com.adnroidapp.modulhw_10.ui.adapter.AdapterMovies
import com.adnroidapp.modulhw_10.ui.adapter.OnItemClickListener
import com.adnroidapp.modulhw_10.ui.network.AndroidNetworkStatus
import com.adnroidapp.modulhw_10.ui.viewModelCoroutine.ViewModelMovieList
import com.adnroidapp.modulhw_10.ui.viewModelCoroutine.mainfacory.MainFactoryMovieList
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

const val MOVIES_KEY = "MOVIES"

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    lateinit var mViewModelMovieList: ViewModelMovieList

    private lateinit var bottomNav: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModelMovieList =
            ViewModelProvider(this,
                MainFactoryMovieList(App.instance, AndroidNetworkStatus(App.instance))).get(
                ViewModelMovieList::class.java)

        initBottomNavigation()

        val recycler = view.findViewById<RecyclerView>(R.id.res_view_move_list).apply {
            adapter = AdapterMovies(click)
        }

        mViewModelMovieList.liveDataMoviesList.observe(viewLifecycleOwner,
            { movie ->
                updateData(movie, recycler)
            })

        mViewModelMovieList.liveDataErrorServerApi.observe(viewLifecycleOwner, { Error ->
            Snackbar.make(view, Error, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun initBottomNavigation() {
        activity?.let {
            bottomNav = it.findViewById(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_popular -> {
                        mViewModelMovieList.loadMoviesMovies(EnumTypeMovie.POPULAR)
                        true
                    }
                    R.id.nav_top -> {
                        mViewModelMovieList.loadMoviesMovies(EnumTypeMovie.TOP)
                        true
                    }
                    R.id.nav_favorite -> {
                        mViewModelMovieList.loadMoviesMovies(EnumTypeMovie.FAVORITE)
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    private fun updateData(list: List<DataDBMovies>?, recycler: RecyclerView) {
        (recycler.adapter as? AdapterMovies)?.bindMovies(list)
    }

    private val click = object : OnItemClickListener {
        override fun onItemClick(id: Long) {
            val bundle = Bundle()
            bundle.putLong(MOVIES_KEY, id)
            findNavController().navigate(
                R.id.action_fragmentMoviesList_to_fragmentMoviesDetails,
                bundle
            )
        }

        override fun onClickLikeMovies(iconLike: Boolean, movie: DataDBMovies) {
            if (iconLike) {
                mViewModelMovieList.deleteMoviesLikeInDb(movie)
            } else {
                mViewModelMovieList.addMovieLikeInDb(movie)
            }
        }
    }
}

