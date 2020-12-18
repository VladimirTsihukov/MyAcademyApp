package com.adnroidapp.modulhw_4.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_4.R
import com.adnroidapp.modulhw_4.adapter.AdapterMovies
import com.adnroidapp.modulhw_4.adapter.OnItemClickListener
import com.adnroidapp.modulhw_4.data.Movies
import com.adnroidapp.modulhw_4.data.MoviesDataSource

const val MOVIES_KEY = "MOVIES"
class FragmentMoviesList: Fragment(R.layout.fragment_movies_list) {

    private var recycler: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.res_view_move_list)
        recycler?.adapter = AdapterMovies(click)

    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        (recycler?.adapter as? AdapterMovies)?.run {
            bindMovies(MoviesDataSource().getMoviesList())
        }
    }

    private val click = object: OnItemClickListener {
        override fun onItemClick(movie: Movies) {
            val bundle = Bundle()
            bundle.putParcelable(MOVIES_KEY, movie)
                findNavController().navigate(R.id.action_fragmentMoviesList_to_fragmentMoviesDetails,
                    bundle)
        }
    }
}

