package com.amaca.starredposts.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amaca.starredposts.ui.framework.data.db.*
import com.amaca.starredposts.ui.framework.data.sources.PostsDataSourceImpl
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class PostDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var postDao: PostDao




    private lateinit var dataSource: PostsDataSourceImpl

    private val waitTime = 3L
    @Before
    fun createDatabase(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val searchAppDatabase = Room.inMemoryDatabaseBuilder(context, StarredPostsDatabase::class.java).allowMainThreadQueries().build()
        postDao = searchAppDatabase.postDao

    }

    @Test
    fun `WhenUserLoadAllPosts_AndApiAndLocalDBAreUnavailable_returnEmpty`() = runBlocking {
        dataSource = PostsDataSourceImpl(postDao, apiUnavailable)
        val postsFromDataSource = dataSource.getAllPosts()
        val posts = postsFromDataSource.getOrAwaitValue(waitTime)
        assertTrue(posts.isEmpty())
    }

    @Test
    fun `WhenUserLoadAllPost_AndApiIsAvailable_returnPostFromDB`() = runBlocking {
        postDao.addPost(localPostEntity)
        dataSource = PostsDataSourceImpl(postDao, api)
        val postsFromDataSource = dataSource.getAllPosts()
        val posts = postsFromDataSource.getOrAwaitValue(waitTime)
        assertEquals(posts[0].title, localPostEntity.title)
    }

    @Test
    fun `WhenUserLoadAllPost_AndApiIsUnavailable_returnPostFromLocalDB`() = runBlocking{
        postDao.addPost(localPostEntity)
        dataSource = PostsDataSourceImpl(postDao, apiUnavailable)
        val postsFromDataSource = dataSource.getAllPosts()
        val posts = postsFromDataSource.getOrAwaitValue(waitTime)
        assertEquals(posts[0].title, localPostEntity.title)
    }

    @Test
    fun `WhenUserLoadAPost_AndApiIsAvailable_returnPostFromLocalDB`() = runBlocking {
        postDao.addPost(localPostEntity)
        dataSource = PostsDataSourceImpl(postDao, api)
        val post = dataSource.get(1)
        assertEquals(post?.title, localPostEntity.title)
    }

    @Test
    fun `WhenUserLoadAPost_AndApiIsUnavailable_returnPostFromLocalDB`() = runBlocking {
        postDao.addPost(localPostEntity)
        dataSource = PostsDataSourceImpl(postDao, apiUnavailable)
        val post = dataSource.get(1)
        assertEquals(post?.title, localPostEntity.title)
    }

    @Test
    fun `WhenUserLoadAPostThatIsMarkedAsFavorite_AndIsAvailable_returnPostMarkedFavorite`() =
        runBlocking {
            val postId = 1
            val postMarkedAsFav = PostEntity(postId, 123, "title", "body", true)
            postDao.addPostAndOverwrite(postMarkedAsFav)

            dataSource = PostsDataSourceImpl(postDao, api)
            val postsLiveData = dataSource.getAllFavoritePosts()

            assertEquals(
                postsLiveData.getOrAwaitValue(1).firstOrNull { it.favorite }?.favorite,
                true
            )
        }


}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}