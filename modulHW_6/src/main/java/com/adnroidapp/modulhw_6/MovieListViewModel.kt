package com.adnroidapp.modulhw_6

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_6.data.Movie
import com.adnroidapp.modulhw_6.data.loadMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    val liveData = MutableLiveData<List<Movie>>()
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        setLoadMovies()
    }

    private fun setLoadMovies() {
        scope.launch {
            val listMovie = loadMovies(getApplication())
            liveData.postValue(listMovie)
        }
    }
}