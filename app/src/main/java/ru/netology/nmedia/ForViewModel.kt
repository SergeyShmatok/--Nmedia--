package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {

    private val repository1 = PostRepositoryModel()

    val data = repository1.get()

    fun like() = repository1.like()

    fun clickOnShare() = repository1.clickOnShare()

    fun clickOnEye() = repository1.clickOnEye()
}
