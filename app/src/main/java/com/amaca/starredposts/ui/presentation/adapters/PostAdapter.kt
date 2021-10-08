package com.amaca.starredposts.ui.presentation.adapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amaca.core.domain.Post
import com.amaca.starredposts.databinding.PostItemBinding


class PostAdapter(val onPostClickListener: (postItem: Post) -> Unit) :
    ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallBack()) {
    class PostViewHolder private constructor(private val postItemBinding: PostItemBinding) :
        RecyclerView.ViewHolder(postItemBinding.root) {
        fun bind(post: Post) {
            if (post.favorite) {
                postItemBinding.favoritePostImage.visibility = VISIBLE
            } else {
                postItemBinding.favoritePostImage.visibility = GONE

            }
            if (post.id <= 20 && !post.alreadyRead) {
                postItemBinding.newPostDotImage.visibility = VISIBLE
            } else {
                postItemBinding.newPostDotImage.visibility = GONE

            }
            postItemBinding.postTitleTxt.text = post.title
        }

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val postItemBinding =
                    PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostViewHolder(postItemBinding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onPostClickListener(getItem(position))
        }
    }


}

class DiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id && oldItem.favorite == newItem.favorite && oldItem.alreadyRead == newItem.alreadyRead
    }

}