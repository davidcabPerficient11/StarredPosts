package com.amaca.starredposts.ui.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amaca.core.domain.Comment
import com.amaca.starredposts.databinding.CommentItemBinding

class CommentAdapter : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(CommentCallback()) {
    class CommentViewHolder private constructor(private val commentItemBinding: CommentItemBinding) :
        RecyclerView.ViewHolder(commentItemBinding.root) {
        fun bind(comment: Comment) {
            commentItemBinding.commentText.text = comment.body
            commentItemBinding.commentEmail.text = comment.email
        }

        companion object {
            fun from(parent: ViewGroup): CommentViewHolder {
                val commentItemBinding = CommentItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return CommentViewHolder(commentItemBinding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class CommentCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.body == newItem.body
    }

}