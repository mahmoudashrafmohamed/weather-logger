package com.dev.mahmoud_ashraf.weather_logger.data.entities

import com.google.gson.annotations.SerializedName

data class Coord (

    @SerializedName("lon") val lon : Double?,
    @SerializedName("lat") val lat : Double?
)