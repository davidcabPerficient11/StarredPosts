package com.amaca.core.interactors

import com.amaca.core.data.PostRepository
import com.amaca.core.domain.Post

class AddPostAndOverwrite(private val postRepository: PostRepository) {
    suspend operator fun invoke(post: Post) = postRepository.addPostFavorite(post)
}