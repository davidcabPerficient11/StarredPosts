package com.amaca.starredposts.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amaca.starredposts.ui.framework.data.db.CommentDao
import com.amaca.starredposts.ui.framework.data.db.StarredPostsDatabase
import com.amaca.starredposts.ui.framework.data.sources.CommentDataSourceImpl
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommentDataSourceImplTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var commentDao: CommentDao

    private lateinit var dataSource: CommentDataSourceImpl

    @Before
    fun createDatabase(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val searchAppDatabase = Room.inMemoryDatabaseBuilder(context, StarredPostsDatabase::class.java).allowMainThreadQueries().build()
        commentDao = searchAppDatabase.commentDao
    }

    @Test
    fun `WhenUserLoadCommentOfAPost_AndApiAndLocalDBAreUnavailable_returnNull`() = runBlocking {
        dataSource = CommentDataSourceImpl(commentDao, apiUnavailable)

        val postsComments = dataSource.getPostComments(1)
        assertNull(postsComments)
    }

    @Test
    fun `WhenUserLoadCommentsOfAPost_AndApiIsAvailable_returnCommentsFromLocalDB`() = runBlocking {
        commentDao.addComment(localCommentEntity)

        dataSource = CommentDataSourceImpl(commentDao, api)

        val postsComments = dataSource.getPostComments(1)
        assertEquals(postsComments!![0].body, localCommentEntity.body)
    }

    @Test
    fun `WhenUserLoadCommentsOfAPost_AndApiIsUnavailable_returnCommentsFromLocalDB`() = runBlocking {
        commentDao.addComment(localCommentEntity)

        dataSource = CommentDataSourceImpl(commentDao, apiUnavailable)

        val postsComments = dataSource.getPostComments(1)
        assertEquals(postsComments!![0].body, localCommentEntity.body)
    }

}