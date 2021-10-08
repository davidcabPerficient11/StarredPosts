package com.amaca.starredposts.ui.presentation.detailed_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.amaca.core.domain.Post
import com.amaca.starredposts.ui.framework.Interactors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedPostViewModel @ViewModelInject constructor(private val interactors: Interactors) :
    ViewModel() {


    private var currentPostID = MutableLiveData<Int>()
    private var currentAuthor = MutableLiveData<Int>()

    val postComments = currentPostID.switchMap {
        liveData(Dispatchers.IO) {
            emit(interactors.postComment(it))
        }
    }

    val author = currentAuthor.switchMap {
        liveData(Dispatchers.IO) {
            emit(interactors.getUser(it))
        }
    }


    fun searchBy(postID: Int, authorID: Int) {
        currentPostID.value = postID
        currentAuthor.value = authorID
    }

    fun addPostToFavorites(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            interactors.addPostAsFavorite(post)
        }
    }

}