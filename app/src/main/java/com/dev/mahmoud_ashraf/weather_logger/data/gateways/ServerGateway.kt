package com.dev.mahmoud_ashraf.weather_logger.data.gateways

import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerGateway {
    @GET("weather")
    fun getWeatherInfo(
        @Query("lat") lat: String,
        @Query("lon") lng: String
    ): Single<WeatherInfoResponse>
}