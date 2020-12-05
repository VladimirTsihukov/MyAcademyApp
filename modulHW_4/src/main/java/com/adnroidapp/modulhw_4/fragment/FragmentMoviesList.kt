package com.adnroidapp.modulhw_4.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.adnroidapp.modulhw_4.R

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.image_film).run {
            setOnClickListener {
                findNavController().navigate(R.id.action_fragmentMoviesList_to_fragmentMoviesDetails)
            }
        }
    }
}
