package com.amaca.core.interactors

import com.amaca.core.data.PostRepository
import com.amaca.core.domain.Post

class AddPost (private val postRepository: PostRepository){
    suspend operator fun invoke(post: Post) = postRepository.addPost(post)
}