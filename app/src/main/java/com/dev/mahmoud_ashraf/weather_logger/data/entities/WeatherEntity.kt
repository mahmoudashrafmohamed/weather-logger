package com.dev.mahmoud_ashraf.weather_logger.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dev.mahmoud_ashraf.weather_logger.data.converters.DateConverter
import java.util.*

@Entity(tableName = "weather_info")
@TypeConverters(DateConverter::class)
data class WeatherEntity(
    @ColumnInfo(name = "temperature") val weatherTemperature: Int,
    @ColumnInfo(name = "timestamp") val requestTime: Date,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "weather_info_id") val id: Long = 0L
)