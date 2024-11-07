package ru.netology.nmedia

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.other_date_and_service.Post


@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
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
{

    fun toDto(): Post = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likes = likes,
        shares = shares,
        views = views,
        likedByMe = likedByMe,
        link = link
    )



    companion object {

        fun fromDto (post: Post): PostEntity =
            with(post) {
                PostEntity(
                    id = id,
                    author = author,
                    content = content,
                    published = published,
                    likes = likes,
                    shares = shares,
                    views = views,
                    likedByMe = likedByMe,
                    link = link
                )
            }
    }

}