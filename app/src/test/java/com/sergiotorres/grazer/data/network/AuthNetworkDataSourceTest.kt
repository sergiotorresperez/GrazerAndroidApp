package com.sergiotorres.grazer.data.network

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sergiotorres.grazer.data.network.dto.Auth
import com.sergiotorres.grazer.data.network.dto.AuthData
import com.sergiotorres.grazer.data.network.dto.AuthRequest
import com.sergiotorres.grazer.data.network.dto.AuthResponse
import com.sergiotorres.grazer.util.TestSingle
import io.reactivex.Single
import org.junit.Test

class AuthNetworkDataSourceTest {

    private val service: GrazerRetrofitService = mock()

    private val underTest = AuthNetworkDataSource(
        service = service
    )

    @Test
    fun `login delegates in network service and returns token when success`() {
        val responseDto = AuthResponse(
            status = 200,
            statusDesc = "whatever",
            auth = Auth(
                data = AuthData(
                    token = "myToken"
                )
            )
        )

        val loginOperation = TestSingle.just(responseDto)
        whenever(service.login(AuthRequest(email = "email", password = "password")))
            .thenReturn(loginOperation)

        underTest.login(email = "email", password = "password")
            .test()
            .assertResult("myToken")

        loginOperation.assertSubscribed()
    }

    @Test
    fun `login delegates from network service and propagates error when fail`() {
        val error: Exception = mock()

        whenever(service.login(AuthRequest(email = "email", password = "password")))
            .thenReturn(Single.error(error))

        underTest.login(email = "email", password = "password")
            .test()
            .assertError(error)
    }
}