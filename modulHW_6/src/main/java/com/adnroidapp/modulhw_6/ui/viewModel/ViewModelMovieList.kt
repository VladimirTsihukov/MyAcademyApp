package com.adnroidapp.modulhw_6.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_6.data.Movie
import com.adnroidapp.modulhw_6.data.loadMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelMovieList(application: Application) : AndroidViewModel(application) {
    val liveDataMovieList = MutableLiveData<List<Movie>>()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        setLoadMovies()
    }

    private fun setLoadMovies() {
        scope.launch {
            val listMovie = loadMovies(getApplication())
            liveDataMovieList.postValue(listMovie)
        }
    }

}