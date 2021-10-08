package com.amaca.starredposts.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amaca.starredposts.ui.framework.data.db.PostDao
import com.amaca.starredposts.ui.framework.data.db.PostEntity
import com.amaca.starredposts.ui.framework.data.db.StarredPostsDatabase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var postDao: PostDao


    @Before
    fun createDatabase(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val searchAppDatabase = Room.inMemoryDatabaseBuilder(context, StarredPostsDatabase::class.java).allowMainThreadQueries().build()
        postDao = searchAppDatabase.postDao

    }

    @Test
    fun `WhenUserSaveAPost_GetTheRightVal`() = runBlocking {
        val allPosts = postDao.getAllPost()
        allPosts.observeForever { }
        postDao.addPost(PostEntity(1, 1, "title", "body"))
        assertEquals(allPosts.value?.size, 1)
    }

    @Test
    fun `WhenUserModifyAPost_GetTheRightVal`() = runBlocking {
        val allPosts = postDao.getAllPost()
        allPosts.observeForever { }
        postDao.addPost(PostEntity(1, 1, "title", "body"))
        assertEquals(allPosts.value?.size, 1)
        assertEquals(allPosts.value!![0].title, "title")

        val newTitle = "new Title"
        val isFavorite = true
        postDao.addPostAndOverwrite(PostEntity(1, 1, newTitle, "body", isFavorite))

        assertEquals( allPosts.value?.size, 1 )
        assertEquals( allPosts.value!![0].title, newTitle)
        assertEquals( allPosts.value!![0].favorite, isFavorite)
    }


}