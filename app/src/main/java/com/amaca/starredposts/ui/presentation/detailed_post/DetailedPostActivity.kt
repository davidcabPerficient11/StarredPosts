package com.amaca.starredposts.ui.presentation.detailed_post

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaca.core.domain.Post
import com.amaca.starredposts.databinding.PostDetailsBinding
import com.amaca.starredposts.ui.presentation.adapters.CommentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedPostActivity : AppCompatActivity() {
    private val binding: PostDetailsBinding by lazy {
        PostDetailsBinding.inflate(layoutInflater)
    }


    private val detailedPostViewModel: DetailedPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val post: Post? = intent.extras?.get(POST_BUNDLE_TAG) as? Post

        post?.let {
            binding.postDesc.text = it.body
            detailedPostViewModel.searchBy(it.id, it.userId)

            binding.favoritePostButton.apply {
                (this as? ImageButton)?.let { starButton ->
                    setFavoriteStar(starButton, post.favorite)
                }
            }.setOnClickListener {
                post.apply {
                    favorite = !favorite
                }.let { post ->
                    (it as? ImageButton)?.let { starButton ->
                        setFavoriteStar(starButton, post.favorite)
                    }


                    detailedPostViewModel.addPostToFavorites(post)
                }

            }
        }



        detailedPostViewModel.author.observe(this) {
            it?.run {
                binding.userEmail.text = email
                binding.userName.text = name
                binding.userPhone.text = phone
                binding.userWebsite.text = website
            }
        }

        val commentAdapter = CommentAdapter()
        binding.commentsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = commentAdapter
        }

        detailedPostViewModel.postComments.observe(this) {
            it?.let { comments ->
                commentAdapter.submitList(comments.toMutableList())

            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


    }

    fun setFavoriteStar(imageButton: ImageButton, isFavorite: Boolean) {
        imageButton.apply {
            if (isFavorite)
                setColorFilter(Color.YELLOW)
            else
                setColorFilter(Color.WHITE)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val POST_BUNDLE_TAG = "POST_BUNDLE_TAG"
    }
}
