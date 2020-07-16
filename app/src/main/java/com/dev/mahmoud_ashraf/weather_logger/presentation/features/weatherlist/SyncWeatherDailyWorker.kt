package com.dev.mahmoud_ashraf.weather_logger.presentation.features.weatherlist

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.dev.mahmoud_ashraf.weather_logger.data.repositories.WeatherInfoRepositoryImp
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import com.dev.mahmoud_ashraf.weather_logger.domain.usecases.fetchWeatherInfo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class SyncWeatherDailyWorker (context: Context, params: WorkerParameters) : RxWorker(context, params),
    KoinComponent {

   private val weatherInfoRepository : WeatherInfoRepository by inject()

    override fun createWork(): Single<Result> {
        return fetchWeatherInfo("30.033333","31.233334",weatherInfoRepository)
            .subscribeOn(Schedulers.io())
            .map {
                Timber.e("result refreshed in worker $it")
              Result.success()
            }
           .onErrorReturn {
               it.printStackTrace()
               Result.retry()
           }
    }


}