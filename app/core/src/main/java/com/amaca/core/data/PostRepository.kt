package com.amaca.core.data

import com.amaca.core.domain.Post

class PostRepository (private val postDataSource: PostDataSource){

    suspend fun getPost(postId: Int) = postDataSource.get(postId)
    fun getAllPosts() = postDataSource.getAllPosts()
    fun getAllFavoritePost() = postDataSource.getAllFavoritePosts()
    suspend fun deleteAllPosts() = postDataSource.removeAllPost()
    suspend fun addPost(post: Post) = postDataSource.add(post)
    suspend fun addPostFavorite(post: Post) = postDataSource.addPostAndOverride(post)
    suspend fun refreshPosts() = postDataSource.refreshPosts()

}