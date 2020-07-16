package com.dev.mahmoud_ashraf.weather_logger.presentation.di


import com.dev.mahmoud_ashraf.weather_logger.BuildConfig
import com.dev.mahmoud_ashraf.weather_logger.data.gateways.ServerGateway
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { provideInterceptor() }
    single {
        provideOkHttpClient(
            get()
        )
    }
    single {
        provideApi(
            get()
        )
    }
    single {
        provideRetrofit(
            get()
        )
    }
}

fun provideInterceptor(): Interceptor {
    return HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor { chain ->

            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("appid", BuildConfig.API_KEY)
                .build()

            // add api key and language
            val requestBuilder = chain.request().newBuilder()
            //requestBuilder.header("language", "ar-AR")
            requestBuilder.url(url)

            val response = chain.proceed(requestBuilder.build())
            response
        }
        .build()
}

fun provideApi(retrofit: Retrofit): ServerGateway = retrofit.create(ServerGateway::class.java)
