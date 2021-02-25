package com.adnroidapp.modulhw_9.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adnroidapp.modulhw_9.apiCorutine.ApiFactoryCoroutine
import com.adnroidapp.modulhw_9.database.SealedMovies
import com.adnroidapp.modulhw_9.database.databaseMoviesList.DbMovies
import com.adnroidapp.modulhw_9.database.dbData.DataDBMoviesPopular
import com.adnroidapp.modulhw_9.pojo.getMovieAllType
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
                val listMovies = ApiFactoryCoroutine.apiServiceMovieCor
                    .getPopularMoviesAsync().body()?.results ?: listOf()
                Log.d(TAG, "WorkerCacheDB: listMovies.isNotEmpty = ${listMovies.isNotEmpty()}")

                if (listMovies.isNotEmpty()) {
                    (getMovieAllType(sealed = SealedMovies.MoviesPopular,
                        list = listMovies) as List<DataDBMoviesPopular>)
                        .let {
                            Log.d(TAG, "Return in getMovieAllType() listDB.size = ${it.size}")
                            db.moviesPopularDao().insertPopularMoviesList(it)
                        }
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