package com.amaca.starredposts.ui.framework.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (@PrimaryKey(autoGenerate = false) val id: Int,
                       val name: String,
                       val email: String,
                       val phone: String,
                       val website: String )