package com.sergiotorres.grazer.domain.repository

import io.reactivex.Completable

interface AuthRepository {
    fun isUserLoggedIn(): Boolean

    fun logIn(email: String, password: String): Completable
}