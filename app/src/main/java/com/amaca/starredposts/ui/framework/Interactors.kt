package com.amaca.starredposts.ui.framework

import com.amaca.core.interactors.*

data class Interactors (
    val addComment: AddComment,
    val addPost: AddPost,
    val addPostAsFavorite: AddPostAndOverwrite,
    val addPostAsReaded: AddPostAndOverwrite,
    val addUser: AddUser,
    val getAllPosts: GetAllPosts,
    val getAllFavoritePosts: GetAllFavoritePosts,
    val postComment: GetComment,
    val getUser: GetUser,
    val refreshPost: RefreshPost,
    val removeAllComments: RemoveAllComments,
    val removeAllPost: RemoveAllPost,
    val removeAllUsers: RemoveAllUsers
)