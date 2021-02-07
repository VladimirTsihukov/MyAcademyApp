package com.adnroidapp.modulhw_8.ui.viewModelRxJava

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.modulhw_8.apiRxJava.ApiFactoryRxJava
import com.adnroidapp.modulhw_8.apiRxJava.ApiServiceRxJava
import com.adnroidapp.modulhw_8.pojo.MovieActors
import com.adnroidapp.modulhw_8.pojo.getMovieDataInfo
import com.adnroidapp.modulhw_8.ui.data.MovieData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelMovieDetailsRxJava(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    val liveDataMoviesDetails = MutableLiveData<MovieData>()
    val liveDataMovieActors = MutableLiveData<MovieActors>()

    fun initMovieId(id: Long) {
        val disposable = ApiFactoryRxJava.apiServiceMoviesRxJava.getMovieInformation(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                liveDataMoviesDetails.postValue(it.getMovieDataInfo())
            }, {
                Log.d(ApiServiceRxJava.TAG_LOAD, it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    fun initMovieActors(id: Long) {
        val disposable = ApiFactoryRxJava.apiServiceMoviesRxJava.getMovieActors(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                liveDataMovieActors.postValue(it)
            },{
                Log.d(ApiServiceRxJava.TAG_LOAD, it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
