//                                            - This is an old code -
//
// package ru.netology.nmedia._Repository
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import ru.netology.nmedia.dao.PostDao
//import ru.netology.nmedia.other_date_and_service.Post
//
//class PostRepositorySQLiteImpl(
//    private val dao: PostDao
//) : PostRepositoryFun {
//    private var posts = emptyList<Post>()
//    private val data = MutableLiveData(posts)
//
////----------------------------- init
//
//    init {
//        posts = dao.getAll()
//        data.value = posts
//    }
//
////----------------------------- Data ----------------------------
//
//    override fun getAll(): LiveData<List<Post>> = data
//
////----------------------------- Fun -----------------------------
//
//
//    override fun save(post: Post) {
//        val id = post.id
//        val saved = dao.save(post)
//        posts = if (id == 0L) {
//            listOf(saved) + posts
//        } else {
//            posts.map {
//                if (it.id != id) it else saved
//            }
//        }
//        data.value = posts
//    }
//
//    override fun likeById(id: Long) {
//        dao.likeById(id)
//        posts = posts.map {
//            if (it.id != id) it else it.copy(
//                likedByMe = !it.likedByMe,
//                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
//            )
//        }
//        data.value = posts
//    }
//
//    override fun clickingShareById(id: Long) {
//        dao.clickingShareById(id)
//        posts = posts.map {
//            if (it.id != id) it else it.copy(shares = it.shares + 1)
//        }
//        data.value = posts    }
//
//    override fun clickingEyeById(id: Long) {
//        dao.clickingEyeById(id)
//        posts = posts.map {
//            if (it.id != id) it else it.copy(views = it.views + 1)
//        }
//        data.value = posts    }
//
//    override fun removeById(id: Long) {
//        dao.removeById(id)
//        posts = posts.filter { it.id != id }
//        data.value = posts
//    }
//
//}
package ru.netology.nmedia._Repository