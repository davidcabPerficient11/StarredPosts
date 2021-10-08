package com.amaca.core.interactors

import com.amaca.core.data.PostRepository

class GetPost(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: Int) = postRepository.getPost(postId)
}