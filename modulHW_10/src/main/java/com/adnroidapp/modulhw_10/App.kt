package com.adnroidapp.modulhw_10

import android.app.Application
import com.adnroidapp.modulhw_10.ui.network.AndroidNetworkStatus

class App : Application() {

    companion object {
        lateinit var instance : App
        lateinit var networkStatus: AndroidNetworkStatus
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        networkStatus = AndroidNetworkStatus(this)
    }
}