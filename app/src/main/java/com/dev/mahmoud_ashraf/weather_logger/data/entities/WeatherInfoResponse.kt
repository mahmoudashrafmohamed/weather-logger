package com.dev.mahmoud_ashraf.weather_logger.data.entities

import com.google.gson.annotations.SerializedName

data class WeatherInfoResponse (
    @SerializedName("coord") val coord : Coord?,
    @SerializedName("main") val main : Main?
)