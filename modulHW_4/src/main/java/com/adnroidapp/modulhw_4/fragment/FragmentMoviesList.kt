package com.adnroidapp.modulhw_4.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_4.R
import com.adnroidapp.modulhw_4.adapter.AdapterMovies
import com.adnroidapp.modulhw_4.data.MoviesDataSource

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var recycler: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<ImageView>(R.id.holder_image_film).run {
//            setOnClickListener {
//                findNavController().navigate(R.id.action_fragmentMoviesList_to_fragmentMoviesDetails)
//            }
//        }

        recycler = view.findViewById(R.id.res_view_move_list)
        recycler?.adapter = AdapterMovies()
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
}
