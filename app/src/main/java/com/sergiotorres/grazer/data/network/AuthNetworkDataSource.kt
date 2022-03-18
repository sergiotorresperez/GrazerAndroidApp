package com.sergiotorres.grazer.data.network

import com.sergiotorres.grazer.data.network.dto.AuthRequest
import io.reactivex.Single
import javax.inject.Inject

class AuthNetworkDataSource @Inject constructor(
    private val service: GrazerRetrofitService
) {

    fun login(email: String, password: String): Single<String> {
        return service.login(AuthRequest(
            email = email,
            password = password
        ))
            .map { response ->
                response.auth.data.token
            }
    }
}
