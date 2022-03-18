package com.sergiotorres.grazer.presentation

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

interface SchedulersProvider {
    fun io() : Scheduler
    fun computation() : Scheduler
    fun main() : Scheduler
}

class RealSchedulersProvider @Inject constructor(): SchedulersProvider {
    override fun io(): Scheduler = io.reactivex.schedulers.Schedulers.io()
    override fun computation(): Scheduler = io.reactivex.schedulers.Schedulers.computation()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}
