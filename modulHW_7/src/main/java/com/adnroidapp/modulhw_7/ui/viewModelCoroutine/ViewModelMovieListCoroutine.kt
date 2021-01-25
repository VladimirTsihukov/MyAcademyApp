package com.adnroidapp.modulhw_7.ui.viewModelCoroutine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_7.apiCorutine.ApiFactoryCoroutine
import com.adnroidapp.modulhw_7.data.MovieData
import com.adnroidapp.modulhw_7.pojo.MoviesList
import com.adnroidapp.modulhw_7.pojo.getMovieData
import kotlinx.coroutines.*
import retrofit2.Response

class ViewModelMovieListCoroutine(application: Application) : AndroidViewModel(application) {

    val liveDataMoviesPopularMovies = MutableLiveData<List<MovieData>>()
    val liveDataErrorServerApi = MutableLiveData<String>()
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        setLoadMoviesPopularMovies()
    }

    fun setLoadMoviesPopularMovies() {
        scope.launch {
            errorHandling(ApiFactoryCoroutine.apiServiceMovieCor.getPopularMoviesAsync())
        }
    }

    fun setLoadMovieTopRate() {
        scope.launch {
            errorHandling(ApiFactoryCoroutine.apiServiceMovieCor.getMovieTopRatedAsync())
        }
    }

    private suspend fun errorHandling(popularMovies: Response<MoviesList>) {
        val movieListResult = mutableListOf<MovieData>()
        if (popularMovies.isSuccessful) {
            popularMovies.body()?.let { result ->
                result.results.forEach {
                    movieListResult.add(it.getMovieData())
                }
            }
            liveDataMoviesPopularMovies.postValue(movieListResult)
        } else {
            withContext(Dispatchers.Main) {
                liveDataErrorServerApi.postValue(popularMovies.code().toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}