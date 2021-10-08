package com.amaca.starredposts.ui.framework.data.sources

import android.util.Log
import com.amaca.core.data.CommentDataSource
import com.amaca.core.domain.Comment
import com.amaca.starredposts.ui.framework.data.db.CommentDao
import com.amaca.starredposts.ui.framework.data.db.CommentEntity
import com.amaca.starredposts.ui.framework.data.remote.PostApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentDataSourceImpl(
    private val commentDao: CommentDao,
    private val postApi: PostApi
) : CommentDataSource{

    override suspend fun add(comment: Comment) = withContext(Dispatchers.IO){
        commentDao.addComment(comment.run {
            return@run CommentEntity(postId, id, email, body)
        }
        )
    }

    override suspend fun getPostComments(id: Int): List<Comment>? = withContext(Dispatchers.IO){
        try {
            return@withContext commentDao.getPostComment(id)
                ?.map {
                    it.run {
                        Comment(postId, id, email, body)
                    }
                }.let {
                    if (it?.isEmpty() == true) {
                        return@let null
                    } else {
                        return@let it
                    }
                }
                ?: postApi.getPostComments(id).body()?.map {
                    commentDao.addComment(it)
                    it.run { Comment(postId, id, email, body) }
                }
        } catch (e: Exception) {
            Log.d("tmp", e.localizedMessage)
            return@withContext null
        }

    }


    override suspend fun removeAllComments() = withContext(Dispatchers.IO) {
        commentDao.deleteAllComents()
    }
}