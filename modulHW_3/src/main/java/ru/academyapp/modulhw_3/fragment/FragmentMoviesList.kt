package ru.academyapp.modulhw_3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.academyapp.modulhw_3.R

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
