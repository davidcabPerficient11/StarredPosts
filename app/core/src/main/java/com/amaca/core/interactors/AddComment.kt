package com.amaca.core.interactors

import com.amaca.core.data.CommentRepository
import com.amaca.core.domain.Comment

class AddComment (private val commentRepository: CommentRepository){
    suspend operator fun invoke(comment: Comment) = commentRepository.addComment(comment)
}