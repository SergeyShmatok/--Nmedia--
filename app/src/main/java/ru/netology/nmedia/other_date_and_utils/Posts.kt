package ru.netology.nmedia.other_date_and_utils

// here are the data-classes: Posts

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val shares: Int = 0,
    val views: Int = 0,
    val likedByMe: Boolean,
    val link: String?,
)