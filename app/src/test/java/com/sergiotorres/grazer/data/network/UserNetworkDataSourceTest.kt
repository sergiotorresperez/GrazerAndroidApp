package com.sergiotorres.grazer.data.network

import com.nhaarman.mockitokotlin2.*
import com.sergiotorres.grazer.data.network.dto.UserListResponse
import com.sergiotorres.grazer.data.network.mapper.UserDtoMapper
import com.sergiotorres.grazer.domain.model.User
import com.sergiotorres.grazer.util.TestSingle
import org.junit.Test

class UserNetworkDataSourceTest {

    private val service: GrazerRetrofitService = mock()
    private val mapper: UserDtoMapper = mock()

    private val underTest = UserNetworkDataSource(
        service = service,
        mapper = mapper
    )

    @Test
    fun `get fetches from network service and returns response mapped to domain model`() {
        val responseDto: UserListResponse = mock()
        val resultDomain: List<User> = mock()

        val fetchOperation = TestSingle.just(responseDto)
        whenever(service.getUsers()).thenReturn(fetchOperation)

        whenever(mapper.mapFromHttpResponse(responseDto)).thenReturn(resultDomain)

        underTest.getUsers()
            .test()
            .assertResult(resultDomain)

        fetchOperation.assertSubscribed()
    }

    @Test
    fun `get fetches from network service and propagates error`() {
        val error: Exception = mock()

        val fetchOperation = TestSingle.error<UserListResponse>(error)
        whenever(service.getUsers()).thenReturn(fetchOperation)

        underTest.getUsers()
            .test()
            .assertError(error)

        fetchOperation.assertSubscribed()
        verify(mapper, never()).mapFromHttpResponse(any())
    }
}
