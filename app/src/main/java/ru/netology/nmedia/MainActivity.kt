package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMain2Binding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            author.text = postNetoGreeting.author
            published.text = postNetoGreeting.published
            content.text = postNetoGreeting.content
            likes.text = clicksInVkFormat(postNetoGreeting.likes)
            shares.text = clicksInVkFormat(postNetoGreeting.shares)
            views.text = clicksInVkFormat(postNetoGreeting.views)


            root.setOnClickListener {
                Log.d("stuff", "root")
            }

            avatar.setOnClickListener {
                Log.d("stuff", "avatar")
            }

            icLikes.setOnClickListener {
                Log.d("stuff", "like")
                postNetoGreeting.likedByMe = !postNetoGreeting.likedByMe
                icLikes.setImageResource(
                    if (postNetoGreeting.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (postNetoGreeting.likedByMe) postNetoGreeting.likes++ else postNetoGreeting.likes--
                likes.text = clicksInVkFormat(postNetoGreeting.likes)


                icShares.setOnClickListener {
                    postNetoGreeting.shares++
                    shares.text = clicksInVkFormat(postNetoGreeting.shares)
                }

                icEye.setOnClickListener {
                    postNetoGreeting.views++
                    views.text = clicksInVkFormat(postNetoGreeting.views)
                }


            }
        }
    }
}
