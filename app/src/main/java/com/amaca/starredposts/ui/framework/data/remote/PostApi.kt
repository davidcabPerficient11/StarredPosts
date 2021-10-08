package com.amaca.starredposts.ui.framework.data.remote

import com.amaca.starredposts.ui.framework.data.db.CommentEntity
import com.amaca.starredposts.ui.framework.data.db.PostEntity
import com.amaca.starredposts.ui.framework.data.db.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostEntity>?>

    @GET("users/{id}")
    suspend fun getPostAuthor(@Path(value="id", encoded = true) id: Int): Response<UserEntity>


    @GET("posts/{id}/comments")
    suspend fun getPostComments(@Path(value="id", encoded = true) id: Int): Response<List<CommentEntity>>

    @GET("posts/{id}")
    suspend fun getPost(@Path(value="id", encoded = true) id: Int): Response<PostEntity>

}