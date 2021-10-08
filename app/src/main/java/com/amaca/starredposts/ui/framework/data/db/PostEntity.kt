package com.amaca.starredposts.ui.framework.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val favorite: Boolean = false,
    val isAlreadyRead: Boolean = false
)
