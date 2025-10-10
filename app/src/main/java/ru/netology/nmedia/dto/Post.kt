package ru.netology.nmedia.dto

data class Post (
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val viewsCount: Int,
    val sharesCount: Int,
    val likedByMe: Boolean = false,
    val likesCount: Int = 0,

)