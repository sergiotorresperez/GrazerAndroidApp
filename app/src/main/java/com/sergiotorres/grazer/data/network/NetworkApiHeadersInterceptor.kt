package com.sergiotorres.grazer.data.network

import com.sergiotorres.grazer.data.local.AuthLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkApiHeadersInterceptor @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
            .header("Authorization", "Bearer ${authLocalDataSource.getAuthToken()}")
            .build()
            .let { chain.proceed(it) }
    }

}