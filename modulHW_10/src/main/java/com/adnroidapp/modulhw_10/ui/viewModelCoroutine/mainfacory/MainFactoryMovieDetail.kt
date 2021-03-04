package com.adnroidapp.modulhw_10.ui.viewModelCoroutine.mainfacory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adnroidapp.modulhw_10.network.INetworkStatus
import com.adnroidapp.modulhw_10.ui.viewModelCoroutine.ViewModelMovieDetails

class MainFactoryMovieDetail (private val application: Application, private val networkStatus: INetworkStatus) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelMovieDetails(application, networkStatus) as T
    }
}