package com.amaca.core.interactors

import com.amaca.core.data.CommentRepository

class RemoveAllComments(private val commentRepository: CommentRepository) {
    suspend operator fun invoke() = commentRepository.removeAllComments()
}