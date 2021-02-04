package com.adnroidapp.modulhw_8.ui.viewModelCoroutine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_8.R
import com.adnroidapp.modulhw_8.apiCorutine.ApiFactoryCoroutine
import com.adnroidapp.modulhw_8.database.DatabaseContact.SEPARATOR
import com.adnroidapp.modulhw_8.database.databaseMoviesList.DbMovies
import com.adnroidapp.modulhw_8.pojo.ActorsInfo
import com.adnroidapp.modulhw_8.pojo.getListActor
import com.adnroidapp.modulhw_8.pojo.getMovieDataInfo
import com.adnroidapp.modulhw_8.pojo.getMovieDetails
import com.adnroidapp.modulhw_8.ui.data.MovieData
import com.adnroidapp.modulhw_8.ui.data.getMovieData
import kotlinx.coroutines.*

class ViewModelMovieDetailsCoroutine(application: Application) : AndroidViewModel(application) {

    private val error = application.resources.getString(R.string.error_internet_not_connect)
    private val dbMovieDetails = DbMovies.instance(application)
    val liveDataMoviesDetailsCoroutine = MutableLiveData<MovieData>()
    val liveDataMovieActorsCoroutine = MutableLiveData<List<ActorsInfo>>()

    val liveDataErrorServerApi = MutableLiveData<String>()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun initMovieIdDetails(id: Long) {
        scope.launch {
            val resultDbDetails = dbMovieDetails.moviesDetails().getMovieDetail(id)
            try {
                val movieDetails =
                    ApiFactoryCoroutine.apiServiceMovieCor.getMovieByIdAsync(id)
                if (movieDetails.isSuccessful) {
                    movieDetails.body()?.let {
                        liveDataMoviesDetailsCoroutine.postValue(it.getMovieDataInfo())
                        dbMovieDetails.moviesDetails().insertMovieDetail(it.getMovieDetails())
                        initMoviesActors(id)
                    }
                } else {
                    getActorsInDb(id)
                    withContext(Dispatchers.Main) {
                        resultDbDetails.let {
                            liveDataMoviesDetailsCoroutine.postValue(getMovieData(it))
                        }
                        liveDataErrorServerApi.postValue(error)
                    }
                }
            } catch (e: Exception) {
                getActorsInDb(id)
                resultDbDetails.let { liveDataMoviesDetailsCoroutine.postValue(getMovieData(it))}
                liveDataErrorServerApi.postValue(error)
            }
        }
    }

    private fun initMoviesActors(id: Long) {
        scope.launch {
            try {
                val movieActors =
                    ApiFactoryCoroutine.apiServiceMovieCor.getMovieActorsCoroutineAsync(id)
                if (movieActors.isSuccessful) {
                    movieActors.body()?.let { MovieActors ->
                        liveDataMovieActorsCoroutine.postValue(MovieActors.getListActor(MovieActors.cast))
                        dbMovieDetails.moviesDetails()
                            .setNameActors(MovieActors.cast.joinToString(SEPARATOR) { it.name }, id)
                        dbMovieDetails.moviesDetails()
                            .setProfilePaths(MovieActors.cast.joinToString(SEPARATOR) {
                                it.profilePath ?: " "
                            }, id)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        getActorsInDb(id)
                        liveDataErrorServerApi.postValue(error)
                    }
                }
            } catch (e: Exception) {
                getActorsInDb(id)
                liveDataErrorServerApi.postValue(error)
            }
        }
    }

    private fun getActorsInDb(id: Long) {
        val listActorsInfo = mutableListOf<ActorsInfo>()
        if (!dbMovieDetails.moviesDetails().getNameActors(id).isNullOrEmpty()) {
            val nameActors = dbMovieDetails.moviesDetails().getNameActors(id).split(SEPARATOR)
            val listProfilePath =
                dbMovieDetails.moviesDetails().getProfilePaths(id).split(SEPARATOR)
            nameActors.forEachIndexed { index, _ ->
                listActorsInfo.add(ActorsInfo(nameActors[index], listProfilePath[index]))
            }
            liveDataMovieActorsCoroutine.postValue(listActorsInfo)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}