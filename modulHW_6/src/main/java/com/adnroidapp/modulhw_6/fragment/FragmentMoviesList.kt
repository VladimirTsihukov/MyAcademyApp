package com.adnroidapp.modulhw_6.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_6.MovieListViewModel
import com.adnroidapp.modulhw_6.R
import com.adnroidapp.modulhw_6.adapter.AdapterMovies
import com.adnroidapp.modulhw_6.adapter.OnItemClickListener
import com.adnroidapp.modulhw_6.data.Movie

const val MOVIES_KEY = "MOVIES"

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var recycler: RecyclerView? = null
    lateinit var mViewModel : MovieListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        recycler = view.findViewById(R.id.res_view_move_list)
        recycler?.adapter = AdapterMovies(click)
        recycler?.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        mViewModel.liveData.observe(this, {
            updateData(it)
            recycler?.visibility = View.VISIBLE
        })
    }

    private fun updateData(list: List<Movie>?) {
        (recycler?.adapter as? AdapterMovies)?.bindMovies(list)
    }

    private val click = object : OnItemClickListener {
        override fun onItemClick(movie: Movie) {
            val bundle = Bundle()
            bundle.putParcelable(MOVIES_KEY, movie)
            findNavController().navigate(
                R.id.action_fragmentMoviesList_to_fragmentMoviesDetails,
                bundle
            )
        }
    }
}

