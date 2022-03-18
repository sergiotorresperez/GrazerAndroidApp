package com.sergiotorres.grazer.util

import com.sergiotorres.grazer.presentation.SchedulersProvider
import io.reactivex.schedulers.Schedulers

class TestSchedulersProvider() : SchedulersProvider {
    override fun io() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()

    override fun main() = Schedulers.trampoline()
}