package com.sergiotorres.grazer.di

import com.sergiotorres.grazer.BuildConfig
import com.sergiotorres.grazer.data.network.GrazerRetrofitService
import com.sergiotorres.grazer.data.network.NetworkApiHeadersInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(networkApiHeadersInterceptor: NetworkApiHeadersInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkApiHeadersInterceptor)
            .addInterceptor(HttpLoggingInterceptor().also { it.setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideGrazerRetrofitService(retrofit: Retrofit): GrazerRetrofitService {
        return retrofit.create(GrazerRetrofitService::class.java)
    }
}
