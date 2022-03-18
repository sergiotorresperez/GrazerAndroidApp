package com.sergiotorres.grazer.di

import android.content.Context
import android.content.SharedPreferences
import com.sergiotorres.grazer.presentation.GrazerApplication
import com.sergiotorres.grazer.presentation.RealSchedulersProvider
import com.sergiotorres.grazer.presentation.SchedulersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApp(): GrazerApplication {
        return GrazerApplication.instance
    }

    @Provides
    fun provideSharedPreferences(app: GrazerApplication): SharedPreferences {
        return app.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {
        @Binds
        fun bindSchedulersProvider(bound: RealSchedulersProvider): SchedulersProvider
    }
}