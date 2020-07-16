package com.dev.mahmoud_ashraf.weather_logger.data.repositories

import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherInfoResponse
import com.dev.mahmoud_ashraf.weather_logger.data.gateways.ServerGateway
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import io.reactivex.Single

class WeatherInfoRepositoryImp(private val serverGateway: ServerGateway) : WeatherInfoRepository {
    override fun getWeatherInfo(lat: String, lng: String): Single<WeatherInfoResponse> {
        return serverGateway.getWeatherInfo(lat, lng)
    }

}