package com.sergiotorres.grazer.presentation.util

data class OneOffEvent<T>(private val event: T) {
    private var consumed = false

    fun get(): T? {
        if (!consumed) {
            synchronized(this) {
                if (!consumed) {
                    consumed = true
                    return event
                }
            }
        }
        return null
    }

    fun runOnce(block: (T) -> Unit) {
        get()?.let { block(it) }
    }
}
