package com.sergiotorres.grazer.data.network.dto

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("status_desc") val statusDesc: String,
    @SerializedName("auth") val auth: Auth
)

data class Auth(
    @SerializedName("data") val data: AuthData
)

data class AuthData(
    @SerializedName("token") val token: String
)