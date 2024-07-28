package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {

    private val repository1 = PostRepositoryModel()

    val data = repository1.getAll()

    fun likeById(id: Long) = repository1.likeById(id)

    fun clickingShareById(id: Long) = repository1.clickingShareById(id)

    fun clickingEyeById(id: Long) = repository1.clickingEyeById(id)
}
