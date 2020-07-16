package com.dev.mahmoud_ashraf.weather_logger.domain.usecases

import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherEntity
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import io.reactivex.Single
import java.util.*


fun fetchCachedWeatherInfo(
    weatherInfoRepository: WeatherInfoRepository
): Single<List<WeatherEntity>> {
    return weatherInfoRepository
        .getLocalWeatherData()
}
