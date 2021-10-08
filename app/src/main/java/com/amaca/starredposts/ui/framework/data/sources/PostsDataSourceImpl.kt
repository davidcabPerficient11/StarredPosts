package com.amaca.starredposts.ui.framework.data.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.amaca.core.data.PostDataSource
import com.amaca.core.domain.Post
import com.amaca.starredposts.ui.framework.data.db.PostDao
import com.amaca.starredposts.ui.framework.data.db.PostEntity
import com.amaca.starredposts.ui.framework.data.remote.PostApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsDataSourceImpl(
    private val postDao: PostDao,
    private val postApi: PostApi
) : PostDataSource {
    override suspend fun add(post: Post) = withContext(Dispatchers.IO) {
        postDao.addPost(with(post) {
            PostEntity(id, userId, title, body, favorite)
        })
    }

    override suspend fun addPostAndOverride(post: Post) {
        postDao.addPostAndOverwrite(with(post) {
            PostEntity(id, userId, title, body, favorite, alreadyRead)
        })
    }


    override suspend fun removeAllPost(): Int = withContext(Dispatchers.IO) {
        postDao.deleteAllPosts()
    }

    override suspend fun get(id: Int): Post? = withContext(Dispatchers.IO) {
        try {
            return@withContext postDao.getPost(id)?.run {
                Post(userId, this.id, title, body, favorite, isAlreadyRead)
            } ?: postApi.getPost(id).body()?.also { postDao.addPost(it) }?.run {
                Post(userId, this.id, title, body, favorite, isAlreadyRead)
            }
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override fun getAllPosts():  LiveData<List<Post>> = liveData(Dispatchers.IO) {
        try {
            postApi.getPosts().body()?.let { allPost ->
                allPost.map { post ->
                    postDao.addPost(post)
                    Post(post.userId, post.id, post.title, post.body, post.favorite)
                }
            }
        } catch (e: Exception) {
        } finally {
            emitSource(Transformations.map(postDao.getAllPost()) {
                return@map it.map { post ->
                    Post(
                        post.userId,
                        post.id,
                        post.title,
                        post.body,
                        post.favorite,
                        post.isAlreadyRead
                    )
                }
            })
        }
    }

    override fun getAllFavoritePosts(): LiveData<List<Post>> = liveData(Dispatchers.IO) {
        emitSource(Transformations.map(postDao.getAllFavoritePost()) { postEntityList ->
            return@map postEntityList.map {
                with(it) {
                    Post(userId, id, title, body, favorite, isAlreadyRead)
                }
            }
        })
    }

    override suspend fun refreshPosts() {
        try {
            postApi.getPosts().body()?.let { allPost ->
                allPost.map { post ->
                    postDao.addPost(post)
                    Post(post.userId, post.id, post.title, post.body, post.favorite)
                }
            }
        } catch (e: Exception) {
        }

    }

}