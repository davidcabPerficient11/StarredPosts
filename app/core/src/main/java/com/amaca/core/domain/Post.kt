package com.amaca.core.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val userId: Int, val id: Int,
    val title: String, val body: String,
    var favorite: Boolean = false,
    var alreadyRead: Boolean = false
) : Parcelable