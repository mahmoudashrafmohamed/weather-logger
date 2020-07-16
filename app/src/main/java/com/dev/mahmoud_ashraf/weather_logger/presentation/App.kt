package com.dev.mahmoud_ashraf.weather_logger.presentation

import android.app.Application
import androidx.work.*
import com.dev.mahmoud_ashraf.weather_logger.BuildConfig
import com.dev.mahmoud_ashraf.weather_logger.presentation.di.networkModule
import com.dev.mahmoud_ashraf.weather_logger.presentation.di.repositoryModule
import com.dev.mahmoud_ashraf.weather_logger.presentation.di.roomModule
import com.dev.mahmoud_ashraf.weather_logger.presentation.di.viewModelModule
import com.dev.mahmoud_ashraf.weather_logger.presentation.features.weatherlist.SyncWeatherDailyWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
        setupWorker()
    }

    private fun setupKoin() {
        startKoin {
            // Koin logger
            androidLogger()
            // inject Android context
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    roomModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    private fun setupWorker(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val updateWeatherWork = PeriodicWorkRequest.Builder(SyncWeatherDailyWorker::class.java,
            60,  TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                60, TimeUnit.SECONDS)
            .addTag("update_app_periodic")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "update_app_unique",
            ExistingPeriodicWorkPolicy.KEEP,
            updateWeatherWork
        )
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}