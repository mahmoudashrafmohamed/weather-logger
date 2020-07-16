# weather-logger

Android application to save weather conditions for your current location.

## Main features
- The weather information should be retrieved from https://openweathermap.org/api > CurrentWeatherData API.
- After ‘Save’ is pressed the application should retrieve the weather data from the API, store it locally together with the date of the event (request time) and then display it on screen.
- Display the data in a list or graphical chart.
- Refresh the weather data periodically.
- Implement unit test for usecases laye.
- Cache data.

## Tech Stack
- kotlin 
- Clean Architecure with MVVM.
- koin for di.
- Rxjava2 and Rx-kotlin.
- Retrofit2.
- Material.
- recycler view with ListAdapter.
- Timber for logging.
- ConstraintLayout.
- viewmodel and live data.
- Glide.
- Jetpack Navigation.
- workmanger with RxWorker
- Room 

## unit test
- Junit4.
- Mockito.

# Note:
 to run this app you need to:

1 - create file apikey.properties in root project

2 - add you api key from the openweathermap ex: API_KEY = "123"

 
