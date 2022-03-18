package com.sergiotorres.grazer.data.network

import com.sergiotorres.grazer.data.network.dto.*
import io.reactivex.Single
import retrofit2.http.*

interface GrazerRetrofitService {

    @POST("/v1/auth/login")
    fun login(
        @Body request: AuthRequest
    ): Single<AuthResponse>

    @GET("/v1/users")
    fun getUsers(): Single<UserListResponse>

}