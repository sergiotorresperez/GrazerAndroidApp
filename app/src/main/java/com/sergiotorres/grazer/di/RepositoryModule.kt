package com.sergiotorres.grazer.di

import com.sergiotorres.grazer.data.AuthRepositoryImpl
import com.sergiotorres.grazer.data.UserRepositoryImpl
import com.sergiotorres.grazer.domain.repository.AuthRepository
import com.sergiotorres.grazer.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        RepositoryModule.Bindings::class,
        NetworkModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {
        @Binds
        fun bindAuthRepository(bound: AuthRepositoryImpl): AuthRepository

        @Binds
        fun bindAUserRepository(bound: UserRepositoryImpl): UserRepository
    }
}
