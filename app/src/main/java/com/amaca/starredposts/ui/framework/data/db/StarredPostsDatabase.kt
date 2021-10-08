package com.amaca.starredposts.ui.framework.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CommentEntity::class, PostEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false)
abstract class StarredPostsDatabase : RoomDatabase(){

    abstract val commentDao: CommentDao
    abstract val postDao: PostDao
    abstract val userDao: UserDao

    companion object{
        @Volatile
        private var INSTANCE: StarredPostsDatabase? = null

        fun getInstance(context: Context): StarredPostsDatabase  {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null){
                    instance =
                        Room.databaseBuilder(context.applicationContext,
                            StarredPostsDatabase::class.java, "starred_posts_database").build()
                }
                return instance
            }
        }
    }

}

