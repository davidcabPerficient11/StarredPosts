package com.amaca.starredposts.ui.presentation.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.amaca.starredposts.ui.framework.Interactors

class FavoritePostsViewModel @ViewModelInject constructor(private val interactors: Interactors) :
    ViewModel() {


    val getAllPosts = interactors.getAllFavoritePosts()


}
