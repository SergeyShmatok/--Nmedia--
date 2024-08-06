package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(object : OnInteractionListener {

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.clickingShareById(post.id)
            }

            override fun onEye(post: Post) {
                viewModel.clickingEyeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        mainBinding.recyclerContainer.adapter = adapter

        viewModel.data.observe(this) {
            val thereIsAnewPost = it.size > adapter.currentList.size
            adapter.submitList(it) {
                if (thereIsAnewPost) {
                    mainBinding.recyclerContainer.smoothScrollToPosition(0)
                }
            }

        }

        mainBinding.send.setOnClickListener {
            with(mainBinding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText(context.getString(R.string.empty_string))
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                mainBinding.group.visibility = View.GONE
                return@observe
            }

            mainBinding.group.visibility = View.VISIBLE
            with(mainBinding.content) {
                mainBinding.editableMessage.text = it.content
                requestFocus()
                setText(it.content)
            }

        }


        mainBinding.icCross.setOnClickListener {
            viewModel.editedIsEmpty()
            with(mainBinding.content) {
                setText(context.getString(R.string.empty_string))
                clearFocus()
                AndroidUtils.hideKeyboard(this)
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
