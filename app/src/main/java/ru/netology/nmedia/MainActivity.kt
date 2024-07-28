package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            { viewModel.likeById(it.id) },
            { viewModel.clickingShareById(it.id) },
            { viewModel.clickingEyeById(it.id) },
        )
        binding.recyclerContainer.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
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
