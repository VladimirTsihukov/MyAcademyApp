package com.adnroidapp.modulhw_10.view.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.adnroidapp.modulhw_10.App
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.data.EnumTypeMovie
import com.adnroidapp.modulhw_10.data.dataApi.Movie
import com.adnroidapp.modulhw_10.data.dataApi.MoviesList
import com.adnroidapp.modulhw_10.data.dataApi.parsInDataDBMovies
import com.adnroidapp.modulhw_10.data.dataDb.DataDBMovies
import com.adnroidapp.modulhw_10.data.dataDb.parsInDataDBMoviesLike
import com.adnroidapp.modulhw_10.data.dataDb.parsInDataDataDBMovies
import com.adnroidapp.modulhw_10.model.api.ApiFactory
import com.adnroidapp.modulhw_10.model.database.databaseMoviesList.DbMovies
import com.adnroidapp.modulhw_10.view.service.WorkerCacheDBMovies
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.concurrent.TimeUnit

const val TAG_IS_ONLINE = "TAG_IS_ONLINE"

class ViewModelMovieList(application: Application) :
    AndroidViewModel(application) {

    private val errorServerNotConnect =
        application.resources.getString(R.string.error_server_connect)

    private val dbMovies = DbMovies.instance(application)

    val liveDataMoviesList = MutableLiveData<List<DataDBMovies>>()
    val liveDataErrorServerApi = MutableLiveData<String>()
    val liveDataCheckInternet = MutableLiveData<Boolean>()
    private val scope = CoroutineScope(Dispatchers.IO)
    private var flagMovieFavorite = false
    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        loadMoviesMovies(EnumTypeMovie.POPULAR)
        checkedInternet()
        startWorkManager(application)
    }

    private fun checkedInternet() {
        disposable.add(App.networkStatus.isOnline().subscribe {
            Log.v(TAG_IS_ONLINE, "CheckedInternet internet connect = $it")
            liveDataCheckInternet.postValue(it)
        })
    }

    fun loadMoviesMovies(typeMovie: EnumTypeMovie) {
        flagMovieFavorite = typeMovie.name == EnumTypeMovie.FAVORITE.name
        if (flagMovieFavorite) {
            getMoviesListLikeForDb()
        } else {
            disposable.add(App.networkStatus.isOnlineSingle().map { online ->
                if (online) {
                    Log.v(TAG_IS_ONLINE, "loadMoviesMovies internet connect = $online")
                    getMovieInServer(typeMovie)
                } else {
                    Log.v(TAG_IS_ONLINE, "loadMoviesMovies internet connect = $online")
                    getMoviesInDb(typeMovie)
                }
            }.subscribe())
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
                liveDataErrorServerApi.postValue(errorServerNotConnect)
            }
        }
    }

    fun deleteMoviesLikeInDb(movie: DataDBMovies) {
        scope.launch {
            try {
                dbMovies.moviesLike().deleteMoviesLike(movie.id)
                dbMovies.movies().setMoviesIdLikeInDb(movie.id, movie.likeMovie)
                if (flagMovieFavorite) {
                    liveDataMoviesList.postValue(parsInDataDataDBMovies(dbMovies.moviesLike()
                        .getMoviesLike()))
                }
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue(errorServerNotConnect)
            }
        }
    }

    private fun getMoviesInDb(typeMovie: EnumTypeMovie) {
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
                            errorHandling(ApiFactory.API_SERVICE_MOVIE.getMovieTopRatedAsync(),
                                typeMovie)
                        }
                        EnumTypeMovie.POPULAR.name -> {
                            errorHandling(ApiFactory.API_SERVICE_MOVIE.getMoviePopularAsync(),
                                typeMovie)
                        }
                    }
                }
            } catch (e: Exception) {
                getMoviesInDb(typeMovie)
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
                getMoviesInDb(typeMovie)
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
        disposable.clear()
    }
}