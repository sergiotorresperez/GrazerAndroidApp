package com.sergiotorres.grazer.data

import com.sergiotorres.grazer.data.local.AuthLocalDataSource
import com.sergiotorres.grazer.data.network.AuthNetworkDataSource
import com.sergiotorres.grazer.domain.repository.AuthRepository
import io.reactivex.Completable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localDataSource: AuthLocalDataSource,
    private val networkDataSource: AuthNetworkDataSource
): AuthRepository {

    override fun isUserLoggedIn(): Boolean {
        return localDataSource.getAuthToken() != null
    }

    override fun logIn(email: String, password: String): Completable {
        return networkDataSource.login(email = email, password = password)
            .doOnSuccess { token ->
                localDataSource.setAuthToken(token)
            }
            .ignoreElement()
    }
}
