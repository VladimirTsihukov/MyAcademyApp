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
import kotlinx.coroutines.withContext

const val MOVIES_KEY = "MOVIES"

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var recycler: RecyclerView? = null
    private var listMovie: List<Movie>? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.res_view_move_list)
        recycler?.adapter = AdapterMovies(click)
        recycler?.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()

        scope.launch {
            listMovie = loadMovies(requireContext())
            withContext(Dispatchers.Main) {
                updateData(listMovie)
                recycler?.visibility = View.VISIBLE
            }
        }
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

