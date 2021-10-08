package com.amaca.core.interactors

import com.amaca.core.data.UserRepository
import com.amaca.core.domain.User

class AddUser (private val userRepository: UserRepository){
    suspend operator fun invoke(user: User) = userRepository.addUser(user)

}