package com.adnroidapp.modulhw_8.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.adnroidapp.modulhw_8.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        findNavController(R.id.nav_host_fragment_container).addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.movie_list -> { bottom_navigation.visibility = View.VISIBLE }
                R.id.movie_detail -> { bottom_navigation.visibility = View.GONE }
            }
        }
    }
}
