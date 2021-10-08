package com.amaca.core.data

import com.amaca.core.domain.User

interface UserDataSource {
    suspend fun add(user: User)
    suspend fun get(id: Int): User?
    suspend fun removeAllUsers(): Int
}