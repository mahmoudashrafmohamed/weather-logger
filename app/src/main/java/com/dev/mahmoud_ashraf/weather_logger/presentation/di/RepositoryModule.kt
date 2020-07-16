package com.dev.mahmoud_ashraf.weather_logger.presentation.di

import com.dev.mahmoud_ashraf.weather_logger.data.gateways.RoomGateway
import com.dev.mahmoud_ashraf.weather_logger.data.gateways.ServerGateway
import com.dev.mahmoud_ashraf.weather_logger.data.repositories.WeatherInfoRepositoryImp
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import org.koin.dsl.module


val repositoryModule = module {
    single {
        provideRepository(
            get(),get()
        )
    }
}

fun provideRepository(serverGateway: ServerGateway, roomGateway: RoomGateway): WeatherInfoRepository {
    return WeatherInfoRepositoryImp(serverGateway,roomGateway)
}