package com.amaca.starredposts.ui.presentation.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaca.starredposts.databinding.FragmentAllPostsBinding
import com.amaca.starredposts.ui.presentation.adapters.PostAdapter
import com.amaca.starredposts.ui.presentation.detailed_post.DetailedPostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePostsFragment : Fragment() {
    private val allFavPostViewModel: FavoritePostsViewModel by viewModels()
    private var _binding: FragmentAllPostsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentAllPostsBinding.inflate(inflater, container, false)

        val postAdapter = PostAdapter { it ->

            Intent(context, DetailedPostActivity::class.java).apply {
                putExtra(DetailedPostActivity.POST_BUNDLE_TAG, it)
                startActivity(this)
            }

        }
        binding.postRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

            adapter = postAdapter
        }
        binding.fab.visibility = GONE

        allFavPostViewModel.getAllPosts.observe(viewLifecycleOwner) {
            postAdapter.submitList(it)
            postAdapter.notifyDataSetChanged()//https://stackoverflow.com/a/50031492
        }
        return binding.root
    }

}
