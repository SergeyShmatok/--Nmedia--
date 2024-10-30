package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia._Repository.PostRepositorySQLiteImpl
import ru.netology.nmedia.other_date_and_service.Post

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    link = "",
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

//----------------------------- Data -----------------------------

    private val repository1 = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao)

    val data = repository1.getAll()

//--------------------------- Function ---------------------------

    fun likeById(id: Long) = repository1.likeById(id)

    fun clickingShareById(id: Long) = repository1.clickingShareById(id)

    fun clickingEyeById(id: Long) = repository1.clickingEyeById(id)

    fun removeById(id: Long) = repository1.removeById(id)

//---------------------------- Editing -----------------------------

    private val edited = MutableLiveData(empty)

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