package com.example.testovoeyandexschedule.di

import com.example.testovoeyandexschedule.BuildConfig
import com.example.testovoeyandexschedule.network.api.ApiRoutes
import com.example.testovoeyandexschedule.network.utils.OffsetDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
open class CoreModule {

    @Provide(cache = Provide.CacheType.Strong)
    open fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(OffsetDateTimeAdapter())
            .build()
    }

    @Provide(cache = Provide.CacheType.Strong)
    open fun provideRetrofit(): Retrofit {
        val token = BuildConfig.API_TOKEN
        //val token = "ce1b5621-25e3-423e-9d5a-059ab171bcfa"

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        val authInterceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", token)
                .build()
            chain.proceed(newRequest)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiRoutes.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
    }
}