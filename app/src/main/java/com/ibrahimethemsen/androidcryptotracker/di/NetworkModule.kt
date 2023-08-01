package com.ibrahimethemsen.androidcryptotracker.di

import com.google.gson.Gson
import com.ibrahimethemsen.androidcryptotracker.network.restful.RestfulService
import com.squareup.moshi.Moshi.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideWebSocketRequest(): Request {
        return Request.Builder().url(SOCKET_URL).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideRestfulService(
        retrofit: Retrofit
    ): RestfulService {
        return retrofit.create(RestfulService::class.java)
    }

    private const val SOCKET_URL = "wss://ws.bitstamp.net"
    private const val BASE_URL = "https://api.coincap.io/"
}