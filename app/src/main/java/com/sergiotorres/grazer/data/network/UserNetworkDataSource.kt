package com.sergiotorres.grazer.data.network

import com.sergiotorres.grazer.data.network.mapper.UserDtoMapper
import com.sergiotorres.grazer.domain.model.User
import io.reactivex.Single
import javax.inject.Inject

class UserNetworkDataSource @Inject constructor(
    private val service: GrazerRetrofitService,
    private val mapper: UserDtoMapper
) {

    fun getUsers(): Single<List<User>> {
        return service.getUsers()
            .map { response ->
                mapper.mapFromHttpResponse(response)
            }
    }
}
