package com.adnroidapp.modulhw_7.ui.viewModelCoroutine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_7.apiCorutine.ApiFactoryCoroutine
import com.adnroidapp.modulhw_7.data.MovieData
import com.adnroidapp.modulhw_7.pojo.MovieActors
import com.adnroidapp.modulhw_7.pojo.getMovieDataInfo
import kotlinx.coroutines.*

class ViewModelMovieDetailsCoroutine(application: Application) : AndroidViewModel(application) {

    val liveDataMoviesDetailsCoroutine = MutableLiveData<MovieData>()
    val liveDataMovieActorsCoroutine = MutableLiveData<MovieActors>()

    val liveDataErrorServerApi = MutableLiveData<String>()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun initMovieIdCoroutine(id: Long) {
        scope.launch {
            try {
                val movieDetails =
                    ApiFactoryCoroutine.apiServiceMovieCor.getMovieByIdAsync(id)
                if (movieDetails.isSuccessful) {
                    movieDetails.body()?.let {
                        liveDataMoviesDetailsCoroutine.postValue(it.getMovieDataInfo())
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        liveDataErrorServerApi.postValue(movieDetails.code().toString())
                    }
                }
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue(e.javaClass.name)
            }
        }
    }

    fun initMoviesActorsCoroutine(id: Long) {
        scope.launch {
            try {
                val movieActors =
                    ApiFactoryCoroutine.apiServiceMovieCor.getMovieActorsCoroutineAsync(id)
                if (movieActors.isSuccessful) {
                    movieActors.body()?.let {
                        liveDataMovieActorsCoroutine.postValue(it)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        liveDataErrorServerApi.postValue(movieActors.code().toString())
                    }
                }
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue(e.javaClass.name)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}