package com.amaca.core.interactors

import com.amaca.core.data.PostRepository

class RefreshPost(private val postRepository: PostRepository) {
    suspend operator fun invoke() = postRepository.refreshPosts()
}