package com.adnroidapp.modulhw_10.view.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adnroidapp.modulhw_10.data.EnumTypeMovie
import com.adnroidapp.modulhw_10.data.dataApi.parsInDataDBMoviesList
import com.adnroidapp.modulhw_10.model.api.ApiFactory
import com.adnroidapp.modulhw_10.model.database.databaseMoviesList.DbMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkerCacheDBMovies(context: Context, workerParam: WorkerParameters) :
    CoroutineWorker(context, workerParam) {
    private val db by lazy { DbMovies.instance(context) }

    override suspend fun doWork(): Result {
        try {
            withContext(Dispatchers.IO) {
                val listMovies = ApiFactory.API_SERVICE_MOVIE
                    .getMoviePopularAsync().body()?.results ?: listOf()

                if (listMovies.isNotEmpty()) {
                    db.movies().insertMovies(parsInDataDBMoviesList(EnumTypeMovie.POPULAR.name,
                        listMovies))
                }
            }
        } catch (error: Throwable) {
            Result.failure()
        }
        return Result.success()
    }
}
