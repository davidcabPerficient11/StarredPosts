package com.amaca.starredposts.ui.presentation.allposts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaca.core.domain.Post
import com.amaca.starredposts.ui.framework.Interactors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AllPostViewModel @ViewModelInject constructor(private val interactors: Interactors) :
    ViewModel() {

    fun markPostAsRead(post: Post) {
        viewModelScope.launch {
            interactors.addPostAsReaded(post)
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            interactors.removeAllComments()
            interactors.removeAllPost()
            interactors.removeAllUsers()
        }

    }

    fun refreshPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            interactors.refreshPost()
        }
    }

    val getAllPosts = interactors.getAllPosts()


}