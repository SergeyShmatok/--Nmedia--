package ru.netology.nmedia._Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.PostEntity
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.other_date_and_service.Post

class PostRepositorySQLRoomImpl(
    private val dao: PostDao
) : PostRepositoryFun {

//----------------------------- Data ----------------------------


    override fun getAll(): LiveData<List<Post>> = dao.getAll().map { postEntities ->
        postEntities.map {
            it.toDto()
        }
    }

//----------------------------- Fun -----------------------------


    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun clickingShareById(id: Long) {
        dao.clickingShareById(id)
    }

    override fun clickingEyeById(id: Long) {
        dao.clickingEyeById(id)

    }

    override fun removeById(id: Long) {
        dao.removeById(id)

    }

}