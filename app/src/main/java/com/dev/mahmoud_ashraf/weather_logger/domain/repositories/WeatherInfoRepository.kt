package com.dev.mahmoud_ashraf.weather_logger.domain.repositories

import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherInfoResponse
import io.reactivex.Single

interface WeatherInfoRepository {
    fun getWeatherInfo(
        lat: String,
        lng: String
    ): Single<WeatherInfoResponse>
}