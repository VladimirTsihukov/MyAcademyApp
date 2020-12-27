package com.adnroidapp.modulhw_6

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_6.data.Movie
import com.adnroidapp.modulhw_6.fragment.MOVIES_KEY

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataMoviesDetails = MutableLiveData<Movie>()

    fun initMovie(argument : Bundle?) {
        liveDataMoviesDetails.value = argument?.getParcelable(MOVIES_KEY)
     }
}