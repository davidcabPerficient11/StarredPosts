package com.amaca.core.interactors

import com.amaca.core.data.PostRepository

class GetAllPosts (private val postRepository: PostRepository) {
    operator fun invoke() = postRepository.getAllPosts()
}