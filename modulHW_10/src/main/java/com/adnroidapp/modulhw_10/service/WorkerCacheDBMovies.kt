package com.adnroidapp.modulhw_10.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adnroidapp.modulhw_10.api.ApiFactory
import com.adnroidapp.modulhw_10.database.databaseMoviesList.DbMovies
import com.adnroidapp.modulhw_10.pojo.parsInDataDBMoviesList
import com.adnroidapp.modulhw_10.ui.EnumTypeMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val TAG = "MyWorker"

class WorkerCacheDBMovies(context: Context, workerParam: WorkerParameters) :
    CoroutineWorker(context, workerParam) {
    private val db by lazy { DbMovies.instance(context) }

    override suspend fun doWork(): Result {
        try {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Start doWorker")
                val listMovies = ApiFactory.API_SERVICE_MOVIE
                    .getMoviePopularAsync().body()?.results ?: listOf()
                Log.d(TAG, "WorkerCacheDB: listMovies.isNotEmpty = ${listMovies.isNotEmpty()}")

                if (listMovies.isNotEmpty()) {
                            db.movies().insertMovies(parsInDataDBMoviesList(EnumTypeMovie.POPULAR.name, listMovies))
                }
                Log.d(TAG, "Stop doWorker")
            }
        } catch (error: Throwable) {
            Log.d(TAG, "Error")
            Result.failure()
        }
        Log.d(TAG, "Result success")
        return Result.success()
    }
}