package com.amaca.core.interactors

import com.amaca.core.data.CommentRepository

class GetComment(private val commentRepository: CommentRepository) {
    suspend operator fun invoke(commentID: Int) = commentRepository.getPostComments(commentID)
}