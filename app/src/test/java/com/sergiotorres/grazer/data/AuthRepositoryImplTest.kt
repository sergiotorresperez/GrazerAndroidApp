package com.sergiotorres.grazer.data

import com.nhaarman.mockitokotlin2.*
import com.sergiotorres.grazer.data.local.AuthLocalDataSource
import com.sergiotorres.grazer.data.network.AuthNetworkDataSource
import com.sergiotorres.grazer.domain.repository.AuthRepository
import com.sergiotorres.grazer.util.TestSingle
import org.junit.Test

class AuthRepositoryImplTest {

    private val localDataSource: AuthLocalDataSource = mock()
    private val networkDataSource: AuthNetworkDataSource = mock()

    private val underTest: AuthRepository = AuthRepositoryImpl(
        localDataSource = localDataSource,
        networkDataSource = networkDataSource
    )

    @Test
    fun `login delegates into network datasource, saves token into local datasource and completes if success`() {
        val networkOperation = TestSingle.just("my_token")
        whenever(networkDataSource.login("email", "password"))
            .thenReturn(networkOperation)

        underTest.logIn("email", "password")
            .test()
            .assertComplete()

        networkOperation.assertSubscribed()
        verify(localDataSource).setAuthToken("my_token")
    }

    @Test
    fun `login delegates into network datasource, does not save token into local datasource and bubbles error if failure`() {
        val error: Exception = mock()

        val networkOperation = TestSingle.error<String>(error)
        whenever(networkDataSource.login("email", "password"))
            .thenReturn(networkOperation)

        underTest.logIn("email", "password")
            .test()
            .assertError(error)

        networkOperation.assertSubscribed()
        verify(localDataSource, never()).setAuthToken(any())
    }
}
