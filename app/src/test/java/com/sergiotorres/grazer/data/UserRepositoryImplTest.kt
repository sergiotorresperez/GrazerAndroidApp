package com.sergiotorres.grazer.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sergiotorres.grazer.data.network.UserNetworkDataSource
import com.sergiotorres.grazer.domain.model.User
import com.sergiotorres.grazer.domain.repository.UserRepository
import com.sergiotorres.grazer.util.TestSingle
import org.junit.Test

class UserRepositoryImplTest {

    private val networkDataSource: UserNetworkDataSource = mock()

    private val underTest: UserRepository = UserRepositoryImpl(
        networkDataSource = networkDataSource
    )

    @Test
    fun `get delegates into network datasource and return loaded users`() {
        val resultDomain: List<User> = mock()

        val getOperation = TestSingle.just(resultDomain)
        whenever(networkDataSource.getUsers()).thenReturn(getOperation)

        underTest.getUsers()
            .test()
            .assertResult(resultDomain)

        getOperation.assertSubscribed()
    }

    @Test
    fun `get delegates into network datasource and bubbles error`() {
        val error: Exception = mock()

        val getOperation = TestSingle.error<List<User>>(error)
        whenever(networkDataSource.getUsers()).thenReturn(getOperation)

        underTest.getUsers()
            .test()
            .assertError(error)

        getOperation.assertSubscribed()
    }
}