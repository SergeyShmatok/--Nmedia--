package ru.netology.nmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
)

class PostViewModel : ViewModel() {

//----------------------------- Data -----------------------------

    private val repository1 = PostRepositoryModel()

    val data = repository1.getAll()

//--------------------------- Function ---------------------------

    fun likeById(id: Long) = repository1.likeById(id)

    fun clickingShareById(id: Long) = repository1.clickingShareById(id)

    fun clickingEyeById(id: Long) = repository1.clickingEyeById(id)

    fun removeById(id: Long) = repository1.removeById(id)

//---------------------------- Editing ----------------------------

    val edited = MutableLiveData(empty)

    fun edit(post: Post) {
        edited.value = post
    }

    fun save() {
        edited.value?.let { repository1.save(it) }
        edited.value = empty
    }

    fun editedIsEmpty () {
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }
}

//------------------------- End