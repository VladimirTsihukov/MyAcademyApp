package com.adnroidapp.modulhw_4.fragment

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_4.R
import com.adnroidapp.modulhw_4.adapter.AdapterActors
import com.adnroidapp.modulhw_4.data.ActorsDataSource

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private val recyclerView: RecyclerView? by lazy { view?.findViewById<RecyclerView>(R.id.rec_actors)?.apply {
        adapter = AdapterActors()
    } }

    override fun onStart() {
        super.onStart()
        updateDataActors()
    }

    private fun updateDataActors() {
        (recyclerView?.adapter as? AdapterActors)?.run {
            bindActors(ActorsDataSource().getMoviesList())
        }
    }
}