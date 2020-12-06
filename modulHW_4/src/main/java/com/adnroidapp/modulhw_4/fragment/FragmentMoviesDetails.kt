package com.adnroidapp.modulhw_4.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_4.R
import com.adnroidapp.modulhw_4.adapter.AdapterActors
import com.adnroidapp.modulhw_4.data.ActorsDataSource

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rec_actors)
        recyclerView?.adapter = AdapterActors()
    }

    override fun onStart() {
        super.onStart()
        updateDataActors()
    }

    private fun updateDataActors() {
        (recyclerView?.adapter as? AdapterActors)?.apply {
            bindActors(ActorsDataSource().getMoviesList())
        }
    }
}