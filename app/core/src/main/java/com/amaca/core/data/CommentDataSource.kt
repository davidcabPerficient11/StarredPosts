package com.amaca.core.data

import com.amaca.core.domain.Comment

interface CommentDataSource {

    suspend fun add(comment: Comment)
    suspend fun getPostComments(id: Int): List<Comment>?
    suspend fun removeAllComments()

}