package com.amaca.core.data

import com.amaca.core.domain.Comment

class CommentRepository(private val commentDataSource: CommentDataSource) {
    suspend fun addComment(comment: Comment) = commentDataSource.add(comment)
    suspend fun getPostComments(id: Int) = commentDataSource.getPostComments(id)
    suspend fun removeAllComments() = commentDataSource.removeAllComments()
}