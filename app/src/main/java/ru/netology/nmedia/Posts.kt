package ru.netology.nmedia

// here are the data-classes: Posts

data class Post(
    val id: Long = 0,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 999,
    val shares: Int = 1699,
    val views: Int = 1_099_999,
    val likedByMe: Boolean = false,
)

