package ru.netology.nmedia

data class Post (
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val viewsCount: Int,
    var sharesCount: Int,
    var likedByMe: Boolean = false,
    var likesCount: Int = 12000000,

)