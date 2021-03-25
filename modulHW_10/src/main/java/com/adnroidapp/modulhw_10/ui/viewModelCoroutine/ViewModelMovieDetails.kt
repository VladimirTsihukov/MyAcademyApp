package com.adnroidapp.modulhw_10.ui.viewModelCoroutine

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.api.ApiFactory
import com.adnroidapp.modulhw_10.database.DatabaseContact.SEPARATOR
import com.adnroidapp.modulhw_10.database.databaseMoviesList.DbMovies
import com.adnroidapp.modulhw_10.database.dbData.DataDBMoviesDetails
import com.adnroidapp.modulhw_10.network.INetworkStatus
import com.adnroidapp.modulhw_10.pojo.ActorsInfo
import com.adnroidapp.modulhw_10.pojo.MovieActors
import com.adnroidapp.modulhw_10.pojo.getListActor
import com.adnroidapp.modulhw_10.pojo.getMovieDetails
import kotlinx.coroutines.*

const val TAG_VIEW_DETAIL = "ViewModelMovieDetails"

class ViewModelMovieDetails(application: Application, private val networkStatus: INetworkStatus) :
    AndroidViewModel(application) {

    private val errorInternetNotConnect =
        application.resources.getString(R.string.error_internet_not_connect)
    private val errorServerNotConnect =
        application.resources.getString(R.string.error_server_connect)
    private val dbMovieDetails = DbMovies.instance(application)
    val liveDataMoviesDetails = MutableLiveData<DataDBMoviesDetails>()
    val liveDataMovieActors = MutableLiveData<List<ActorsInfo>>()

    val liveDataErrorServerApi = MutableLiveData<String>()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun loadMovieIdDetails(id: Long) {
        networkStatus.isOnlineSingle().map { online ->
            if (online) {
                Log.v(TAG_IS_ONLINE, "loadMovieIdDetails internet connect = $online")
                loadMovieDetailInServer(id)
            } else {
                Log.v(TAG_IS_ONLINE, "loadMovieIdDetails internet connect = $online")
                loadMovieDetailInDb(id)
                liveDataErrorServerApi.postValue(errorInternetNotConnect)
            }
        }.subscribe()
    }

    private fun loadMovieDetailInServer(id: Long) {
        scope.launch {
            try {
                val movieDetails =
                    ApiFactory.API_SERVICE_MOVIE.getMovieByIdAsync(id)
                if (movieDetails.isSuccessful) {
                    movieDetails.body()?.let {
                        liveDataMoviesDetails.postValue(it.getMovieDetails())
                        dbMovieDetails.moviesDetails().insertMovieDetail(it.getMovieDetails())
                        loadMoviesActors(id)
                    }
                } else {
                    loadMovieDetailInDb(id)
                    liveDataErrorServerApi.postValue(errorServerNotConnect)
                }
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue(errorServerNotConnect)
            }
        }
    }

    private fun loadMovieDetailInDb(id: Long) {
        scope.launch {
            loadActorsInDb(id)
            val resultDbDetails = dbMovieDetails.moviesDetails().getMovieDetail(id)
            resultDbDetails?.let { liveDataMoviesDetails.postValue(it) }
        }
    }

    private fun loadMoviesActors(id: Long) {
        networkStatus.isOnlineSingle().map { online ->
            if (online) {
                loadActorsInServer(id)
            } else {
                loadActorsInDb(id)
                liveDataErrorServerApi.postValue(errorInternetNotConnect)
            }
        }.subscribe()
    }

    private fun loadActorsInServer(id: Long) {
        scope.launch {
            try {
                val movieActors =
                    ApiFactory.API_SERVICE_MOVIE.getMovieActorsCoroutineAsync(id)
                if (movieActors.isSuccessful) {
                    movieActors.body()?.let { MovieActors ->
                        liveDataMovieActors.postValue(getListActor(MovieActors.cast))
                        loadActorsInDb(MovieActors, id)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        loadActorsInDb(id)
                        liveDataErrorServerApi.postValue(errorInternetNotConnect)
                    }
                }
            } catch (e: Exception) {
                loadActorsInDb(id)
                liveDataErrorServerApi.postValue(errorInternetNotConnect)
            }
        }
    }

    private fun loadActorsInDb(MovieActors: MovieActors, id: Long) {
        dbMovieDetails.moviesDetails()
            .setNameActors(MovieActors.cast.joinToString(SEPARATOR) { it.name }, id)
        dbMovieDetails.moviesDetails()
            .setProfilePaths(MovieActors.cast.joinToString(SEPARATOR) {
                it.profilePath ?: " "
            }, id)
    }

    private fun loadActorsInDb(id: Long) {
        val listActorsInfo = mutableListOf<ActorsInfo>()
        if (!dbMovieDetails.moviesDetails().getNameActors(id).isNullOrEmpty()) {
            val nameActors = dbMovieDetails.moviesDetails().getNameActors(id).split(SEPARATOR)
            val listProfilePath =
                dbMovieDetails.moviesDetails().getProfilePaths(id).split(SEPARATOR)
            nameActors.forEachIndexed { index, _ ->
                listActorsInfo.add(ActorsInfo(nameActors[index], listProfilePath[index]))
            }
            liveDataMovieActors.postValue(listActorsInfo)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}