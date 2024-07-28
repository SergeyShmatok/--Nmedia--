package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
typealias OnEyeListener = (post: Post) -> Unit

//-------------------- PostAdapter -------------------

class PostAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onEyeListener: OnEyeListener,
) :
    ListAdapter<Post, PostHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view =
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(
            view, onLikeListener,
            onShareListener,
            onEyeListener,
        )
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

//--------------------- PostHolder ---------------------

class PostHolder(
    private val view: CardPostBinding, private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onEyeListener: OnEyeListener,
) :
    RecyclerView.ViewHolder(view.root) {
    fun bind(post: Post) {
        view.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = clicksInVkFormat(post.likes)
            shares.text = clicksInVkFormat(post.shares)
            views.text = clicksInVkFormat(post.views)

            icLikes.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )

            icLikes.setOnClickListener {
                onLikeListener(post)
            }
            icShares.setOnClickListener {
                onShareListener(post)
            }

            icEye.setOnClickListener {
                onEyeListener(post)
            }

        }
    }
}

//---------------------- DiffUtil -----------------------


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

//------------------------- End --------------------------
