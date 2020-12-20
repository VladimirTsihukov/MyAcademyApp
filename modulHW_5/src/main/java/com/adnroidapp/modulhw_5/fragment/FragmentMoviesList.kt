package com.adnroidapp.modulhw_5.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_5.R
import com.adnroidapp.modulhw_5.adapter.AdapterMovies
import com.adnroidapp.modulhw_5.adapter.OnItemClickListener
import com.adnroidapp.modulhw_5.data.Movie
import com.adnroidapp.modulhw_5.data.loadMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MOVIES_KEY = "MOVIES"

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var recycler: RecyclerView? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private var listMovie: List<Movie>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.res_view_move_list)
        recycler?.adapter = AdapterMovies(click)

    }

    override fun onStart() {
        super.onStart()

        scope.launch {
                listMovie = activity?.applicationContext?.let { loadMovies(it) }
                updateData(listMovie)
        }
    }

    private fun updateData(list: List<Movie>?) {
        (recycler?.adapter as? AdapterMovies)?.run {
            bindMovies(list)
        }
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

