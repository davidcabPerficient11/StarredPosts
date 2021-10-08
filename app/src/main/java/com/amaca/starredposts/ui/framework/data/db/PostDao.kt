package com.amaca.starredposts.ui.framework.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface PostDao {
    @Insert(onConflict = IGNORE)
    suspend fun addPost(post: PostEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addPostAndOverwrite(post: PostEntity)

    @Query("SELECT * FROM posts")
    fun getAllPost(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE favorite=1")
    fun getAllFavoritePost(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id= :id")
    suspend fun getPost(id: Int): PostEntity?

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts(): Int
}