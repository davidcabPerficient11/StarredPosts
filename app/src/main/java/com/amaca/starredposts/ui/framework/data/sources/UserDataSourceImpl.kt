package com.amaca.starredposts.ui.framework.data.sources

import com.amaca.core.data.UserDataSource
import com.amaca.core.domain.User
import com.amaca.starredposts.ui.framework.data.db.UserDao
import com.amaca.starredposts.ui.framework.data.db.UserEntity
import com.amaca.starredposts.ui.framework.data.remote.PostApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDataSourceImpl(
    private val userDao: UserDao,
    private val postApi: PostApi
    ) : UserDataSource{
    override suspend fun add(user: User) = withContext(Dispatchers.IO) {
        userDao.addUser(
            with(user){
                UserEntity(id, name, email, phone, website)
            }
        )

    }


    override suspend fun get(id: Int): User? = withContext(Dispatchers.IO){
        try {
            return@withContext userDao.getUser(id)?.run {
                User(id, name, email, phone, website)
            } ?: postApi.getPostAuthor(id).body()?.also { userDao.addUser(it) }?.run {
                User(id, name, email, phone, website)
            }
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override suspend fun removeAllUsers(): Int = withContext(Dispatchers.IO) {
        userDao.deleteAllUsers()
    }
}