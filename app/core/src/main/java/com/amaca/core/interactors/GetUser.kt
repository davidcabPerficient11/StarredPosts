package com.amaca.core.interactors

import com.amaca.core.data.UserRepository

class GetUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(userID: Int) = userRepository.get(userID)
}