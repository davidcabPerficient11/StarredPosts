package com.amaca.core.interactors

import com.amaca.core.data.PostRepository

class RemoveAllPost(private val postRepository: PostRepository) {

    suspend operator fun invoke() = postRepository.deleteAllPosts()

}