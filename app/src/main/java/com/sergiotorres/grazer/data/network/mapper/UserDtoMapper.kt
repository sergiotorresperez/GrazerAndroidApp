package com.sergiotorres.grazer.data.network.mapper

import com.sergiotorres.grazer.data.network.dto.UserDto
import com.sergiotorres.grazer.data.network.dto.UserListResponse
import com.sergiotorres.grazer.domain.model.User
import org.joda.time.DateTimeZone
import org.joda.time.Instant
import javax.inject.Inject


/**
 * Mapper for [UserListResponse] between the Grazer HTTP API representation representation in the data layer and the domain layer
 */
class UserDtoMapper @Inject constructor() {

    /**
     * Maps one [UserListResponse] (Grazer HTTP API representation) to one [List<User>] (domain representation)
     */
    fun mapFromHttpResponse(input: UserListResponse): List<User> {
        return input.data.users.map { mapFromHttpResponse(it) }
    }

    private fun mapFromHttpResponse(userDto: UserDto): User {
        return User(
            name = userDto.name,
            profileImage = userDto.profileImage,
            dateOfBirth = Instant.ofEpochMilli(userDto.dateOfBirth * 1000).toDateTime(DateTimeZone.getDefault()).toLocalDate()
        )
    }
}