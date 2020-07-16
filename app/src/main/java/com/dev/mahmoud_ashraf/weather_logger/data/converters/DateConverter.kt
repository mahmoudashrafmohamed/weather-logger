package com.dev.mahmoud_ashraf.weather_logger.data.converters


import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun dateToLong(date: Date) = date.time

    @TypeConverter
    fun longToDate(time: Long) = Date(time)
}