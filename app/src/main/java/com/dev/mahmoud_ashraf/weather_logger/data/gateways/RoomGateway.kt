package com.dev.mahmoud_ashraf.weather_logger.data.gateways

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherEntity
import io.reactivex.Single

@Dao
interface RoomGateway {

    @Insert(onConflict = IGNORE)
    fun saveWeatherData(data: WeatherEntity) : Single<Long>

    @Query("SELECT * FROM weather_info ORDER BY timestamp DESC LIMIT 5;")
     fun getWeatherData(): Single<List<WeatherEntity>>

}

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun roomDao(): RoomGateway
}