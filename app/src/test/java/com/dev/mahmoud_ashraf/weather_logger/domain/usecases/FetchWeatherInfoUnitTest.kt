package com.dev.mahmoud_ashraf.weather_logger.domain.usecases

import com.dev.mahmoud_ashraf.weather_logger.data.entities.Coord
import com.dev.mahmoud_ashraf.weather_logger.data.entities.Main
import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherEntity
import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherInfoResponse
import com.dev.mahmoud_ashraf.weather_logger.domain.core.RxImmediateSchedulerRule
import com.dev.mahmoud_ashraf.weather_logger.domain.repositories.WeatherInfoRepository
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class FetchWeatherInfoUnitTest {

    @Mock
    lateinit var weatherInfoRepository: WeatherInfoRepository

    // to test rx java with different schedulers
    @Rule
    @JvmField
    var rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Test
    fun `fetchWeatherInfo() with lat and long will return Weather info List in this location`() {

        // arrange
        val lat = "30.033333"
        val long = "31.233334"

        val weatherList = mutableListOf<WeatherEntity>()

        weatherList.add(WeatherEntity(100, Date(2000),1))



        Mockito.`when`(weatherInfoRepository.getRemoteWeatherInfo(anyString(), anyString()))
            .thenReturn(
                Single.just(
                    WeatherInfoResponse(
                        Coord(10.0,20.0),
                      Main(100.0,1.0,1.0,2.0,11,1)
                    )
                )
            )

        Mockito.`when`(weatherInfoRepository.getLocalWeatherData())
            .thenReturn(
                Single.just(
                   weatherList
                )
            )

        // act
        val resultObserver = fetchCachedWeatherInfo(lat,long, weatherInfoRepository).test()

        // assert
        val expected = mutableListOf<WeatherEntity>()
        expected.add(WeatherEntity(100, Date(2000),1))

        resultObserver.assertValue(expected)
        resultObserver.dispose()
    }

    @Test
    fun `fetchWeatherInfo() with non null lat and long will call repo`() {
        // arrange
        val lat = "30.033333"
        val long = "31.233334"

        // act
        val testObserver = fetchWeatherInfo(lat, long, weatherInfoRepository).test()

        // assert
        verify(weatherInfoRepository).getRemoteWeatherInfo(lat,long)

        testObserver.dispose()
    }

}