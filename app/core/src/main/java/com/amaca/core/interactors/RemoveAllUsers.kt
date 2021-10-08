package com.amaca.core.interactors

import com.amaca.core.data.UserRepository

class RemoveAllUsers (private val userRepository: UserRepository){
    suspend operator fun invoke() = userRepository.removeAllUsers()
}
