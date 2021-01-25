package com.adnroidapp.modulhw_8.ui.viewModelRxJava

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_8.apiRxJava.ApiFactoryRxJava
import com.adnroidapp.modulhw_8.apiRxJava.ApiServiceRxJava
import com.adnroidapp.modulhw_8.data.MovieData
import com.adnroidapp.modulhw_8.pojo.getMovieData
import com.adnroidapp.modulhw_8.pojo.getMovieDataInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ViewModelMovieListRxJava(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    val liveDataMoviesInformation = MutableLiveData<List<MovieData>>()
    val liveDataMoviesTopRate = MutableLiveData<List<MovieData>>()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        setLoadMovieTopRate()
    }

    private fun setLoadMovieID() {
        val disposable = ApiFactoryRxJava.apiServiceMoviesRxJava.getMoviesTopRated()
            .map { movieList -> movieList.results.map { it.id } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setLiveDataMoviesInformation(it)
                Log.d(ApiServiceRxJava.TAG_LOAD, it.toString())
            }, {
                Log.d(ApiServiceRxJava.TAG_LOAD, it.message.toString())
            })

        compositeDisposable.add(disposable)
    }

    private fun setLiveDataMoviesInformation(list: List<Long>) {
        val disposable = ApiFactoryRxJava.apiServiceMoviesRxJava
        val listInformationMovies: MutableList<MovieData> = mutableListOf()
        list.forEach { id ->
            disposable.getMovieInformation(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listInformationMovies.add(it.getMovieDataInfo())
                    liveDataMoviesInformation.postValue(listInformationMovies)
                }, {
                    Log.d(ApiServiceRxJava.TAG_LOAD, it.message.toString())
                })
        }
    }

    private fun setLoadMovieTopRate() {
        val moviesListResult = mutableListOf<MovieData>()
        val disposable = ApiFactoryRxJava.apiServiceMoviesRxJava.getMoviesTopRated()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList ->
                movieList.results.forEach {
                    moviesListResult.add(it.getMovieData())
                }
                liveDataMoviesTopRate.postValue(moviesListResult)
                Log.d(ApiServiceRxJava.TAG_LOAD, movieList.toString())
            }, {
                Log.d(ApiServiceRxJava.TAG_LOAD, it.message.toString())
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}