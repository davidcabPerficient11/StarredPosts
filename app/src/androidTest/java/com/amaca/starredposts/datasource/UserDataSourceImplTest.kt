package com.amaca.starredposts.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amaca.starredposts.ui.framework.data.db.StarredPostsDatabase
import com.amaca.starredposts.ui.framework.data.db.UserDao
import com.amaca.starredposts.ui.framework.data.sources.UserDataSourceImpl
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var userDao: UserDao

    private lateinit var dataSource: UserDataSourceImpl

    @Before
    fun createDatabase(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val searchAppDatabase = Room.inMemoryDatabaseBuilder(context, StarredPostsDatabase::class.java).allowMainThreadQueries().build()
        userDao = searchAppDatabase.userDao
    }

    @Test
    fun `WhenUserLoadAuthorOfAPost_AndApiAndLocalDBAreUnavailable_returnNothing`() = runBlocking {
        dataSource = UserDataSourceImpl(userDao, apiUnavailable)
        val user = dataSource.get(1)
        assertNull(user)
    }

    @Test
    fun `WhenUserLoadAuthorOfAPost_AndApiIsAvailable_returnCommentsFromLocalDB`() = runBlocking {
        userDao.addUser(localUserEntity)

        dataSource = UserDataSourceImpl(userDao, api)

        val user = dataSource.get(1)
        assertEquals(user?.name, localUserEntity.name)
    }

    @Test
    fun `WhenUserLoadAuthorOfAPost_AndApiIsUnavailable_returnAuthorFromLocalDB`() = runBlocking {
        userDao.addUser(localUserEntity)

        dataSource = UserDataSourceImpl(userDao, apiUnavailable)

        val user = dataSource.get(1)
        assertEquals(user?.name, localUserEntity.name)
    }
}