package com.dev.mahmoud_ashraf.weather_logger.presentation.features.weatherlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherEntity
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import com.dev.mahmoud_ashraf.weather_logger.domain.usecases.fetchCachedWeatherInfo
import com.dev.mahmoud_ashraf.weather_logger.domain.usecases.fetchWeatherInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class WeatherListViewModel(private val weatherInfoRepository: WeatherInfoRepository) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _weatherListLiveData = MutableLiveData<List<WeatherEntity>>()
    val weatherListLiveData : LiveData<List<WeatherEntity>>
        get() = _weatherListLiveData

    init {
        fetchCachedWeatherInfo(weatherInfoRepository)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("result $it")
                    _weatherListLiveData.value = it
                },
                { throwable ->
                    throwable.printStackTrace()
                }

            ).addTo(compositeDisposable)
    }


    fun refresh(latitude: Double?, longitude: Double?) {
        fetchWeatherInfo(latitude?.toString(),longitude?.toString(),weatherInfoRepository)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("result refreshed $it")
                    _weatherListLiveData.value = it
                },
                { throwable ->
                    throwable.printStackTrace()
                }

            ).addTo(compositeDisposable)

    }
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}