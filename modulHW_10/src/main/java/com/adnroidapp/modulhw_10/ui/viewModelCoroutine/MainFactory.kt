package com.adnroidapp.modulhw_10.ui.viewModelCoroutine

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adnroidapp.modulhw_10.network.INetworkStatus

class MainFactory(private val application: Application, private val networkStatus: INetworkStatus) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelMovieList(application, networkStatus) as T
    }
}