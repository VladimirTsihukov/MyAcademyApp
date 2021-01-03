package com.adnroidapp.modulhw_7.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_7.ui.viewModel.ViewModelMovieList
import com.adnroidapp.modulhw_7.R
import com.adnroidapp.modulhw_7.ui.adapter.AdapterMovies
import com.adnroidapp.modulhw_7.ui.adapter.OnItemClickListener
import com.adnroidapp.modulhw_7.data.Movie

const val MOVIES_KEY = "MOVIES"

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val mViewModelMovieList : ViewModelMovieList by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val recycler = view.findViewById<RecyclerView>(R.id.res_view_move_list).apply {
            adapter = AdapterMovies(click)
            visibility = View.INVISIBLE
        }

        mViewModelMovieList.liveDataMovieList.observe(viewLifecycleOwner, { movie ->
                updateData(movie, recycler)
                recycler.visibility = View.VISIBLE
        })
    }

    private fun updateData(list: List<Movie>?, recycler: RecyclerView) {
        (recycler.adapter as? AdapterMovies)?.bindMovies(list)
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

