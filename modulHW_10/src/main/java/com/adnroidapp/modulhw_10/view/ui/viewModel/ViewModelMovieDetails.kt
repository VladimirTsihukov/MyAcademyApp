package com.adnroidapp.modulhw_10.view.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_10.App
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.data.dataApi.ActorsInfo
import com.adnroidapp.modulhw_10.data.dataApi.MovieActors
import com.adnroidapp.modulhw_10.data.dataApi.getListActor
import com.adnroidapp.modulhw_10.data.dataApi.getMovieDetails
import com.adnroidapp.modulhw_10.data.dataDb.DataDBMoviesDetails
import com.adnroidapp.modulhw_10.model.api.ApiFactory
import com.adnroidapp.modulhw_10.model.database.DatabaseContact.SEPARATOR
import com.adnroidapp.modulhw_10.model.database.databaseMoviesList.DbMovies
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

class ViewModelMovieDetails(application: Application) :
    AndroidViewModel(application) {
    private val errorServerNotConnect =
        application.resources.getString(R.string.error_server_connect)
    private val dbMovieDetails = DbMovies.instance(application)
    val liveDataMoviesDetails = MutableLiveData<DataDBMoviesDetails>()
    val liveDataMovieActors = MutableLiveData<List<ActorsInfo>>()

    val liveDataErrorServerApi = MutableLiveData<String>()

    private val scope = CoroutineScope(Dispatchers.IO)
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun loadMovieIdDetails(id: Long) {
        disposable.add(App.networkStatus.isOnlineSingle().map { online ->
            if (online) {
                Log.v(TAG_IS_ONLINE, "loadMovieIdDetails internet connect = $online")
                loadMovieDetailInServer(id)
            } else {
                Log.v(TAG_IS_ONLINE, "loadMovieIdDetails internet connect = $online")
                loadMovieDetailInDb(id)
            }
        }.subscribe())
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
        disposable.add(App.networkStatus.isOnlineSingle().map { online ->
            if (online) {
                loadActorsInServer(id)
            } else {
                loadActorsInDb(id)
            }
        }.subscribe())
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
                    }
                }
            } catch (e: Exception) {
                loadActorsInDb(id)
            }
        }
    }

    private fun loadActorsInDb(MovieActors: MovieActors, id: Long) {
        dbMovieDetails.moviesDetails()
            .setNameActors(MovieActors.cast.joinToString(SEPARATOR) { it.name }, id)
        dbMovieDetails.moviesDetails()
            .setProfilePaths(MovieActors.cast.joinToString(SEPARATOR) {
                it.profilePath ?: " " }, id)
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
        disposable.clear()
    }
}