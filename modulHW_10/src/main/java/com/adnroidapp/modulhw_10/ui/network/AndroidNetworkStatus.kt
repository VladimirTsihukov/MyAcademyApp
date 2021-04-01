package com.adnroidapp.modulhw_10.ui.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class AndroidNetworkStatus(context: Application) {

    private val statusSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetworkInfo
        if (network == null) statusSubject.onNext(false) else statusSubject.onNext(network.isConnected)

        val request = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    statusSubject.onNext(true)
                }

                override fun onLost(network: Network) {
                    statusSubject.onNext(false)
                }

                override fun onUnavailable() {
                    statusSubject.onNext(false)
                }
            })
    }

    fun isOnline(): Observable<Boolean> = statusSubject
    fun isOnlineSingle(): Single<Boolean> = statusSubject.first(false)
}