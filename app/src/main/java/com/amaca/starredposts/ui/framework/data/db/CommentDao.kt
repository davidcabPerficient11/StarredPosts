package com.amaca.starredposts.ui.framework.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CommentDao {
    @Insert(onConflict = REPLACE)
    suspend fun addComment(comment: CommentEntity)

    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getPostComment(postId: Int): List<CommentEntity>?

    @Query("DELETE FROM comments")
    suspend fun deleteAllComents()


}