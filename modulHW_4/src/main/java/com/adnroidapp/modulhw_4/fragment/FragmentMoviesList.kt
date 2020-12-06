package com.adnroidapp.modulhw_4.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_4.MainActivity
import com.adnroidapp.modulhw_4.R
import com.adnroidapp.modulhw_4.adapter.AdapterMovies
import com.adnroidapp.modulhw_4.adapter.OnItemClickListener
import com.adnroidapp.modulhw_4.data.Movies
import com.adnroidapp.modulhw_4.data.MoviesDataSource

class FragmentMoviesList: Fragment(R.layout.fragment_movies_list), OnItemClickListener {

    private var recycler: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.res_view_move_list)
        recycler?.adapter = AdapterMovies(this)
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        (recycler?.adapter as? AdapterMovies)?.apply {
            bindMovies(MoviesDataSource().getMoviesList())
        }
    }

    override fun onItemClick(movie: Movies) {
        (requireActivity() as MainActivity).run {
            findNavController().navigate(R.id.action_fragmentMoviesList_to_fragmentMoviesDetails)
        }
    }
}
