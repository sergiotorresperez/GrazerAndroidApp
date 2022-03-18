package com.sergiotorres.grazer.util

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals


/**
 * Utility class to assert that we have subscribed to a [Observable]
 */
class TestObservable<T>(private val delegate: Observable<T>) : Observable<T>() {

    private val testObserver = TestObserver.create<T>()

    private var actualSubscriptionsCount = 0

    @SuppressLint("CheckResult")
    override fun subscribeActual(observer: Observer<in T>?) {
        delegate.doOnSubscribe {d -> testObserver.onSubscribe(d)}
            .doOnSubscribe { actualSubscriptionsCount += 1 }
            .subscribeWith(observer)
    }

    fun assertSubscribed(expectedSubscriptionCount: Int = 1) {
        testObserver.assertSubscribed()
        assertEquals("Subscriptions count", expectedSubscriptionCount, actualSubscriptionsCount)
    }

    fun assertNotSubscribed() {
        testObserver.assertNotSubscribed()
    }

    companion object {
        fun <T> empty() = TestObservable(Observable.empty<T>())
        fun <T> just(value: T) = TestObservable(Observable.just(value))
        fun <T> never() = TestObservable(Observable.never<T>())
        fun <T> error(t: Throwable) = TestObservable(Observable.error<T>(t))
    }

}