package com.adnroidapp.modulhw_10.ui.viewModelCoroutine

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.apiCorutine.ApiFactoryCoroutine
import com.adnroidapp.modulhw_10.ui.EnumTypeMovie
import com.adnroidapp.modulhw_10.database.databaseMoviesList.DbMovies
import com.adnroidapp.modulhw_10.database.dbData.DataDBMovies
import com.adnroidapp.modulhw_10.database.dbData.parsInDataDBMoviesLike
import com.adnroidapp.modulhw_10.database.dbData.parsInDataDataDBMovies
import com.adnroidapp.modulhw_10.network.INetworkStatus
import com.adnroidapp.modulhw_10.pojo.Movie
import com.adnroidapp.modulhw_10.pojo.MoviesList
import com.adnroidapp.modulhw_10.pojo.parsInDataDBMovies
import com.adnroidapp.modulhw_10.service.WorkerCacheDBMovies
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.concurrent.TimeUnit

const val TAG_IS_ONLINE = "TAG_IS_ONLINE"

class ViewModelMovieList(application: Application, private val networkStatus: INetworkStatus) :
    AndroidViewModel(application) {

    private val errorInternetNotConnect =
        application.resources.getString(R.string.error_internet_not_connect)
    private val errorServerNotConnect =
        application.resources.getString(R.string.error_server_connect)

    private val dbMovies = DbMovies.instance(application)

    val liveDataMoviesList = MutableLiveData<List<DataDBMovies>>()
    val liveDataErrorServerApi = MutableLiveData<String>()
    private val scope = CoroutineScope(Dispatchers.IO)
    var movieFavorite = false

    init {
        loadMoviesMovies(EnumTypeMovie.POPULAR)
        startWorkManager(application)
    }

    fun loadMoviesMovies(typeMovie: EnumTypeMovie) {
        movieFavorite = typeMovie.name == EnumTypeMovie.FAVORITE.name
        if (movieFavorite)  {
            getMoviesListLikeForDb()
        } else {
            networkStatus.isOnlineSingle().map { online ->
                Log.v(TAG_IS_ONLINE, "loadMovies ${typeMovie.name} is online = $online")
                if (online) {
                    getMovieInServer(typeMovie)
                } else {
                    geTMoviesInDb(typeMovie)
                    liveDataErrorServerApi.postValue(errorInternetNotConnect)
                }
            }.subscribe()
        }
    }

   private fun getMoviesListLikeForDb() {
        scope.launch {
            val moviesLike = dbMovies.moviesLike().getMoviesLike()
            liveDataMoviesList.postValue(parsInDataDataDBMovies(moviesLike))
        }
    }

    private fun startWorkManager(application: Application) {
        val const = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val constrainRequest =
            PeriodicWorkRequest.Builder(WorkerCacheDBMovies::class.java, 8, TimeUnit.HOURS)
                .setConstraints(const)
                .build()
        WorkManager.getInstance(application)
            .enqueueUniquePeriodicWork("Update DB",
                ExistingPeriodicWorkPolicy.KEEP,
                constrainRequest)
    }

    fun addMovieLikeInDb(movie: DataDBMovies) {
        scope.launch {
            try {
                dbMovies.moviesLike().insetMoviesLike(movie.parsInDataDBMoviesLike())
                dbMovies.movies().setMoviesIdLikeInDb(movie.id, movie.likeMovie)
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue("Error on db")
            }
        }
    }

    fun deleteMoviesLikeInDb(movie: DataDBMovies) {
        scope.launch {
            try {
                dbMovies.moviesLike().deleteMoviesLike(movie.id)
                dbMovies.movies().setMoviesIdLikeInDb(movie.id, movie.likeMovie)
                if (movieFavorite) {
                    liveDataMoviesList.postValue(parsInDataDataDBMovies(dbMovies.moviesLike()
                        .getMoviesLike()))
                }
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue("Error on db")
            }
        }
    }

    private fun geTMoviesInDb(typeMovie: EnumTypeMovie) {
        scope.launch {
            withContext(Dispatchers.IO) {
                when (typeMovie.name) {
                    EnumTypeMovie.POPULAR.name ->
                        liveDataMoviesList.postValue(dbMovies.movies()
                            .getMoviesCategory(typeMovie.name))
                    EnumTypeMovie.TOP.name ->
                        liveDataMoviesList.postValue(dbMovies.movies()
                            .getMoviesCategory(typeMovie.name))
                }
            }
        }
    }

    private fun getMovieInServer(typeMovie: EnumTypeMovie) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    when (typeMovie.name) {
                        EnumTypeMovie.TOP.name -> {
                            errorHandling(ApiFactoryCoroutine.apiServiceMovieCor.getMovieTopRatedAsync(),
                                typeMovie)
                        }
                        EnumTypeMovie.POPULAR.name -> {
                            errorHandling(ApiFactoryCoroutine.apiServiceMovieCor.getMoviePopularAsync(),
                                typeMovie)
                        }
                    }
                }
            } catch (e: Exception) {
                geTMoviesInDb(typeMovie)
                liveDataErrorServerApi.postValue(errorServerNotConnect)
            }
        }
    }

    private suspend fun errorHandling(movies: Response<MoviesList>, typeMovie: EnumTypeMovie) {
        if (movies.isSuccessful) {
            movies.body()?.let { Movies ->
                processResultDataInApi(Movies.results, typeMovie)
            }
        } else {
            withContext(Dispatchers.Main) {
                geTMoviesInDb(typeMovie)
                liveDataErrorServerApi.postValue(errorServerNotConnect)
            }
        }
    }

    private fun processResultDataInApi(listMovies: List<Movie>, typeMovie: EnumTypeMovie) {
        val movieListResult = mutableListOf<DataDBMovies>()
        val listIdMovieLike = dbMovies.moviesLike().getAllID()
        listMovies.forEach {
            it.typeMovie = typeMovie.name
            if (listIdMovieLike.contains(it.id)) {
                it.likeMovie = true
            }
            movieListResult.add(it.parsInDataDBMovies())
        }
        dbMovies.movies().insertMovies(movieListResult)
        liveDataMoviesList.postValue(movieListResult)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}