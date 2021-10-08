package com.amaca.core.interactors

import com.amaca.core.data.PostRepository

class GetAllFavoritePosts(private val postRepository: PostRepository) {
    operator fun invoke() = postRepository.getAllFavoritePost()
}