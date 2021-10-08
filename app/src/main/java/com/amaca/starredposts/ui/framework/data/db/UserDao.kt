package com.amaca.starredposts.ui.framework.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id= :id")
    suspend fun getUser(id: Int): UserEntity?

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers(): Int
}