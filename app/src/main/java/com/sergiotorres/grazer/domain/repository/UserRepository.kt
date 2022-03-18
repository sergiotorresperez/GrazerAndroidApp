package com.sergiotorres.grazer.domain.repository

import com.sergiotorres.grazer.domain.model.User
import io.reactivex.Single

interface UserRepository {
    fun getUsers(): Single<List<User>>
}