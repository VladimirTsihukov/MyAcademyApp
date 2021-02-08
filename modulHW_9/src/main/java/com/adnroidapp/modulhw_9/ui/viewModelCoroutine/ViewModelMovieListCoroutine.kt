package com.adnroidapp.modulhw_9.ui.viewModelCoroutine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.adnroidapp.modulhw_9.R
import com.adnroidapp.modulhw_9.apiCorutine.ApiFactoryCoroutine
import com.adnroidapp.modulhw_9.database.SealedMovies
import com.adnroidapp.modulhw_9.database.databaseMoviesList.DbMovies
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesPopular
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesTopRate
import com.adnroidapp.modulhw_9.pojo.MoviesList
import com.adnroidapp.modulhw_9.pojo.getMovieAllType
import com.adnroidapp.modulhw_9.pojo.getMovieData
import com.adnroidapp.modulhw_9.service.WorkerCacheDBMovies
import com.adnroidapp.modulhw_9.ui.data.MovieData
import com.adnroidapp.modulhw_9.ui.data.getListMovieData
import com.adnroidapp.modulhw_9.ui.data.getMovieLike
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ViewModelMovieListCoroutine(application: Application) : AndroidViewModel(application) {

    private val error = application.resources.getString(R.string.error_internet_not_connect)
    private val dbMovies = DbMovies.instance(application)

    val liveDataMoviesList = MutableLiveData<List<MovieData>>()
    val liveDataErrorServerApi = MutableLiveData<String>()
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        setLoadMoviesPopularMovies()

        val const = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)                      //TODO("SetRequiresCharging not work")
            .build()

        val constrainRequest = PeriodicWorkRequest.Builder(WorkerCacheDBMovies::class.java, 8, TimeUnit.HOURS)
            .setConstraints(const)
            .build()
        WorkManager.getInstance(application)
            .enqueueUniquePeriodicWork("Update DB", ExistingPeriodicWorkPolicy.KEEP, constrainRequest)

/*         Testing workManager
        val constrainRequest = OneTimeWorkRequest.Builder(WorkerCacheDB::class.java)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(const)
            .build()

        WorkManager.getInstance(application)
            .enqueue(constrainRequest)*/
    }

    fun setLoadMoviesPopularMovies() {
        scope.launch {
            try {
                errorHandling(ApiFactoryCoroutine.apiServiceMovieCor.getPopularMoviesAsync(),
                    SealedMovies.MoviesPopular)
            } catch (e: Exception) {
                getDBMovies(SealedMovies.MoviesPopular)
                liveDataErrorServerApi.postValue(error)
            }
        }
    }

    fun setLoadMovieTopRate() {
        scope.launch {
            try {
                errorHandling(ApiFactoryCoroutine.apiServiceMovieCor.getMovieTopRatedAsync(),
                    SealedMovies.MoviesTopRate)
            } catch (e: Exception) {
                getDBMovies(SealedMovies.MoviesTopRate)
                liveDataErrorServerApi.postValue(error)
            }
        }
    }

    fun getMoviesListLike() {
        scope.launch {
            try {
                val moviesLike = dbMovies.moviesListLike().getAllMoviesLike()
                liveDataMoviesList.postValue(getListMovieData(SealedMovies.MoviesLike, moviesLike))
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue("Error: $e")
            }
        }
    }

    fun addMovieLikeInDb(movie: MovieData) {
        scope.launch {
            try {
                dbMovies.moviesListLike().insertMovieLike(movie.getMovieLike())
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue("Error on db")
            }
        }
    }

    fun deleteMoviesLikeInDb(movie: MovieData) {
        scope.launch {
            try {
                dbMovies.moviesListLike().deleteMovieLike(movie.getMovieLike())
                liveDataMoviesList.postValue(getListMovieData(SealedMovies.MoviesLike,
                    dbMovies.moviesListLike().getAllMoviesLike()))
            } catch (e: Exception) {
                liveDataErrorServerApi.postValue("Error on db")
            }
        }
    }

    private suspend fun errorHandling(
        popularMovies: Response<MoviesList>,
        sealedMovies: SealedMovies,
    ) {
        val movieListResult = mutableListOf<MovieData>()
        val listIdDetails = dbMovies.moviesListLike().getAllId()
        if (popularMovies.isSuccessful) {
            popularMovies.body()?.let { listMovies ->

                listMovies.results.forEach {
                    if (listIdDetails.contains(it.id)) {
                        it.likeMovies = true
                    }
                    movieListResult.add(it.getMovieData())
                }

                when (sealedMovies) {
                    SealedMovies.MoviesPopular -> {
                        (getMovieAllType(SealedMovies.MoviesPopular,
                            list = listMovies.results) as List<DataDBMoviesPopular>).let {
                            dbMovies.moviesPopularDao().insertPopularMoviesList(it)
                        }
                    }
                    SealedMovies.MoviesTopRate -> {
                        (getMovieAllType(SealedMovies.MoviesTopRate,
                            list = listMovies.results) as List<DataDBMoviesTopRate>).let {
                            dbMovies.moviesTopRateDao().insetTopMoviesList(it)
                        }
                    }
                    else -> liveDataErrorServerApi.postValue("Error: not this movie value")
                }
            }
            liveDataMoviesList.postValue(movieListResult)
        } else {
            withContext(Dispatchers.Main) {
                liveDataErrorServerApi.postValue(popularMovies.code().toString())
            }
        }
    }

    private fun getDBMovies(sealedMovies: SealedMovies) {
        scope.launch {
            withContext(Dispatchers.IO) {
                when (sealedMovies) {
                    SealedMovies.MoviesPopular ->
                        liveDataMoviesList.postValue(getListMovieData(sealedMovies,
                            dbMovies.moviesPopularDao()
                                .getPopularMoviesList()))

                    SealedMovies.MoviesTopRate ->
                        liveDataMoviesList.postValue(getListMovieData(sealedMovies,
                            dbMovies.moviesTopRateDao()
                                .getTopRateMoviesList()))
                    SealedMovies.MoviesLike -> liveDataErrorServerApi.postValue("Type MoviesLike not found")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}