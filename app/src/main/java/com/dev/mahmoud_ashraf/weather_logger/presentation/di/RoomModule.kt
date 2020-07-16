package com.dev.mahmoud_ashraf.weather_logger.presentation.di

import androidx.room.Room
import com.dev.mahmoud_ashraf.weather_logger.data.gateways.DatabaseManager
import com.dev.mahmoud_ashraf.weather_logger.data.gateways.RoomGateway
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single { Room.databaseBuilder(androidContext(), DatabaseManager::class.java, "weather_database").build() }

    single { get<DatabaseManager>().roomDao() }

}