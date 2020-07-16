package com.dev.mahmoud_ashraf.weather_logger.presentation.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import com.dev.mahmoud_ashraf.weather_logger.domain.usecases.fetchWeatherInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class WeatherListViewModel(private val weatherInfoRepository: WeatherInfoRepository) : ViewModel() {
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun call(){
        fetchWeatherInfo("30.033333","31.233334",weatherInfoRepository)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("result",it.toString())
//                    val temp = popularActorsLiveData.value?.toMutableList() ?: mutableListOf()
//                    temp.addAll(actors)
//                    _popularActorsLiveData.value = temp
//                    PAGE_NUM++
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