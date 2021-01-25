package com.adnroidapp.modulhw_8.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_8.R
import com.adnroidapp.modulhw_8.data.MovieData
import com.adnroidapp.modulhw_8.ui.adapter.AdapterMovies
import com.adnroidapp.modulhw_8.ui.adapter.OnItemClickListener
import com.adnroidapp.modulhw_8.ui.viewModelCoroutine.ViewModelMovieListCoroutine
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

const val MOVIES_KEY = "MOVIES"

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val mViewModelMovieListCoroutine: ViewModelMovieListCoroutine by viewModels()

    private lateinit var bottomNav: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomNavigation()

        val recycler = view.findViewById<RecyclerView>(R.id.res_view_move_list).apply {
            adapter = AdapterMovies(click)
            visibility = View.INVISIBLE
        }

        mViewModelMovieListCoroutine.liveDataMoviesPopularMovies.observe(viewLifecycleOwner,
            { movie ->
                updateData(movie, recycler)
                recycler.visibility = View.VISIBLE
            })

        mViewModelMovieListCoroutine.liveDataErrorServerApi.observe(viewLifecycleOwner, {Error ->
            Snackbar.make(view, "Error $Error", Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun initBottomNavigation() {
        activity?.let {
            bottomNav = it.findViewById(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_popular -> {
                        mViewModelMovieListCoroutine.setLoadMoviesPopularMovies()
                        true
                    }
                    R.id.nav_top -> {
                        mViewModelMovieListCoroutine.setLoadMovieTopRate()
                        true
                    }
                    R.id.nav_search -> {
                        Toast.makeText(it, "Search", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    private fun updateData(list: List<MovieData>?, recycler: RecyclerView) {
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
    }
}

