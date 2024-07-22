package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMain2Binding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) {
            with(binding) {
                author.text = it.author
                published.text = it.published
                content.text = it.content
                likes.text = clicksInVkFormat(it.likes)
                shares.text = clicksInVkFormat(it.shares)
                views.text = clicksInVkFormat(it.views)
                icLikes.setImageResource(
                    if (it.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
            }
        }

        with(binding) {
            icLikes.setOnClickListener {
                viewModel.like()
            }

            icShares.setOnClickListener {
                viewModel.clickOnShare()
            }

            icEye.setOnClickListener {
                viewModel.clickOnEye()
            }
        }
    }
}

//            root.setOnClickListener {
//                Log.d("stuff", "root")
//            }
//
//            avatar.setOnClickListener {
//                Log.d("stuff", "avatar")
//            }
//                Log.d("stuff", "like")
