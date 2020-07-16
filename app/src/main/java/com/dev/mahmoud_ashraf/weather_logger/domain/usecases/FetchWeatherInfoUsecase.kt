package com.dev.mahmoud_ashraf.weather_logger.domain.usecases

import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherInfoResponse
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import io.reactivex.Single


fun fetchWeatherInfo(
    lat: String?,
    lng: String?,
    weatherInfoRepository: WeatherInfoRepository
): Single<WeatherInfoResponse> {
    return weatherInfoRepository
        .takeIf { lat!=null && lng!=null }
        ?.getWeatherInfo(lat!!,lng!!)
        ?.map {
          it
        }
        ?: Single.error(Exception())

}
