package com.amaca.starredposts.ui.framework.data.db

import androidx.room.Entity

@Entity(tableName = "comments", primaryKeys = arrayOf("postId", "id"))
data class CommentEntity(val postId: Int,
                         val id: Int, val email: String, val body: String)