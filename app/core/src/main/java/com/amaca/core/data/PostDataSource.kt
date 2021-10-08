package com.amaca.core.data

import androidx.lifecycle.LiveData
import com.amaca.core.domain.Post

interface PostDataSource {
    suspend fun add(post: Post)
    suspend fun addPostAndOverride(post: Post)

    suspend fun removeAllPost(): Int
    suspend fun get(id: Int): Post?
    fun getAllPosts(): LiveData<List<Post>> // Well, this violates the CA principle, but since we want to go with LiveData instead of Flows, we can use a Generic type but.... ¯\_(ツ)_/¯
    fun getAllFavoritePosts(): LiveData<List<Post>>
    suspend fun refreshPosts()

}