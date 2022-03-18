package com.sergiotorres.grazer.util

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.observers.TestObserver
import org.junit.Assert

/**
 * Utility class to assert that we have subscribed to a [Completable]
 */
class TestCompletable(private val delegate: Completable) : Completable() {

    private val testObserver = TestObserver.create<Void>()

    private var actualSubscriptionsCount = 0

    override fun subscribeActual(observer: CompletableObserver) {
        delegate.doOnSubscribe {d -> testObserver.onSubscribe(d)}
            .doOnSubscribe { actualSubscriptionsCount += 1 }
            .subscribe(observer)
    }

    fun assertSubscribed(expectedSubscriptionCount: Int = 1) {
        testObserver.assertSubscribed()
        Assert.assertEquals("Subscriptions count", expectedSubscriptionCount, actualSubscriptionsCount)
    }

    fun assertNotSubscribed() {
        testObserver.assertNotSubscribed()
    }

    companion object {
        fun complete() = TestCompletable(Completable.complete())
        fun never() = TestCompletable(Completable.never())
        fun error(t: Throwable) = TestCompletable(Completable.error(t))
    }
}
