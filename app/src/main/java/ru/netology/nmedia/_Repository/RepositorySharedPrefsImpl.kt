package ru.netology.nmedia._Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.other_date_and_service.Post


class PostRepositorySharedPrefsImpl(context: Context) : PostRepositoryFun {


    private var nextId: Long = 1

    private var posts = emptyList<Post>()
        set(value) {
            field = value
            sync()
        }

    private val data = MutableLiveData(posts)


    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)


    companion object {

        private val gson = Gson()
        private const val KEY = "posts"
        private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    }


    init {
        prefs.getString(KEY, null)?.let { it ->
            posts = gson.fromJson(it, typeToken)
            nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
            data.value = posts
        }
    }

//---------------------- Fun -------------------------


    private fun sync() {
        prefs.edit().apply {
            putString(KEY, gson.toJson(posts))
            apply()
        }
    }


    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id != id) post else post.copy(
                likedByMe = !post.likedByMe,
                likes = if (!post.likedByMe) post.likes + 1 else post.likes - 1
            )
        }

        data.value = posts
    }

    override fun clickingShareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }

    override fun clickingEyeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(views = it.views + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {

        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    published = "now",
                    likedByMe = false,
                )
            ) + posts
            data.value = posts
            return
        }

        posts =
            posts.map { if (it.id != post.id) it else it.copy(content = post.content) }
        data.value = posts
    }

}

//----------------------- End
