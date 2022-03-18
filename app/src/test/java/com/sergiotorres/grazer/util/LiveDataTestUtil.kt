package com.sergiotorres.grazer

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock

fun <T> LiveData<T>.observeForTesting(block: (Observer<T>) -> Unit) {
    val observer = mock<Observer<T>>()
    try {
        observeForever(observer)
        block(observer)
    } finally {
        removeObserver(observer)
    }
}

fun <T> LiveData<T>.observeForTesting(): Observer<T> {
    val observer = mock<Observer<T>>()
    observeForever(observer)
    return observer
}