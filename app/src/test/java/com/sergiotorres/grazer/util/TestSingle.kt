package com.sergiotorres.grazer.util

import android.annotation.SuppressLint
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals

/**
 * Utility class to assert that we have subscribed to a [Single]
 */
class TestSingle<T>(private val delegate: Single<T>) : Single<T>() {

    private val testObserver = TestObserver.create<T>()

    private var actualSubscriptionsCount = 0

    @SuppressLint("CheckResult")
    override fun subscribeActual(observer: SingleObserver<in T>) {
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
        fun <T> just(value: T) = TestSingle(Single.just(value))
        fun <T> never() = TestSingle(Single.never<T>())
        fun <T> error(t: Throwable) = TestSingle(Single.error<T>(t))
    }

}
