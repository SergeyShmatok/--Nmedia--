package ru.netology.nmedia.dao

import ru.netology.nmedia.other_date_and_service.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun clickingShareById(id: Long)
    fun clickingEyeById(id: Long)
}