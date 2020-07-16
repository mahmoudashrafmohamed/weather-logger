package com.dev.mahmoud_ashraf.weather_logger.data.repositories

import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherEntity
import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherInfoResponse
import com.dev.mahmoud_ashraf.weather_logger.data.gateways.RoomGateway
import com.dev.mahmoud_ashraf.weather_logger.data.gateways.ServerGateway
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import io.reactivex.Single

class WeatherInfoRepositoryImp(private val serverGateway: ServerGateway,private val roomGateway: RoomGateway) : WeatherInfoRepository {
    override fun getRemoteWeatherInfo(lat: String, lng: String): Single<WeatherInfoResponse> {
        return serverGateway.getWeatherInfo(lat, lng)
    }

    override fun getLocalWeatherData(): Single<List<WeatherEntity>> {
      return roomGateway.getWeatherData()
    }

    override fun saveWeatherData(data: WeatherEntity): Single<Long> {
        return roomGateway.saveWeatherData(data)
    }

}