package com.dev.mahmoud_ashraf.weather_logger.domain.repositories

import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherEntity
import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherInfoResponse
import io.reactivex.Single

interface WeatherInfoRepository {

    fun getRemoteWeatherInfo(
        lat: String,
        lng: String
    ): Single<WeatherInfoResponse>

    fun getLocalWeatherData(): Single<List<WeatherEntity>>

    fun saveWeatherData(data: WeatherEntity) : Single<Long>

}