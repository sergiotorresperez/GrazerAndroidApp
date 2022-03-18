package com.sergiotorres.grazer.data

import com.sergiotorres.grazer.data.network.UserNetworkDataSource
import com.sergiotorres.grazer.domain.model.User
import com.sergiotorres.grazer.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

// NOTE: this repository seems silly now, as it just delegates into the network datasource.
// I think it's good to have this extra layer of repo - datasources to integrate secondary datasources.
// One example would be injecting a datasoruce based in a database, so that we would persist the list
// of users for offline mode.
class UserRepositoryImpl @Inject constructor(
    private val networkDataSource: UserNetworkDataSource
): UserRepository {

    override fun getUsers(): Single<List<User>> {
        return networkDataSource.getUsers()
    }
}
