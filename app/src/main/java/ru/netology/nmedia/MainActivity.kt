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
                author.text = postNetoGreeting.author
                published.text = postNetoGreeting.published
                content.text = postNetoGreeting.content
                likes.text = clicksInVkFormat(postNetoGreeting.likes)
                shares.text = clicksInVkFormat(postNetoGreeting.shares)
                views.text = clicksInVkFormat(postNetoGreeting.views)
                icLikes.setImageResource(
                    if (postNetoGreeting.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
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
