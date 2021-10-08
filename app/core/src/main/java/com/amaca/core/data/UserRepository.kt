package com.amaca.core.data

import com.amaca.core.domain.User

class UserRepository (private val userDataSource : UserDataSource) {

    suspend fun addUser(user: User) = userDataSource.add(user)
    suspend fun get(id: Int) = userDataSource.get(id)
    suspend fun removeAllUsers() = userDataSource.removeAllUsers()

}