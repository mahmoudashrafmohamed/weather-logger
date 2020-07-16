package com.dev.mahmoud_ashraf.weather_logger.presentation.di

import com.dev.mahmoud_ashraf.weather_logger.presentation.features.weatherlist.WeatherListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WeatherListViewModel(
            get()
        )
    }
}