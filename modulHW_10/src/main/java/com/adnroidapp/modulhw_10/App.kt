package com.adnroidapp.modulhw_10

import android.app.Application
import com.adnroidapp.modulhw_10.view.network.NetworkStatus

class App : Application() {

    companion object {
        lateinit var instance : App
        lateinit var networkStatus: NetworkStatus
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        networkStatus = NetworkStatus(this)
    }
}