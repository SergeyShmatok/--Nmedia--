package ru.netology.nmedia._Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.OnInteractionListener
import ru.netology.nmedia.other_date_and_service.Post
import ru.netology.nmedia.PostAdapter
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostContract) {
            it ?: let { viewModel.editedIsEmpty(); return@registerForActivityResult }
            viewModel.run { changeContent(it); save() }
        }

        mainBinding.plus.setOnClickListener { newPostLauncher.launch(null) }

        val adapter = PostAdapter(object : OnInteractionListener {

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {

                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content) // - устанавливает контент
                    // Внутри Intent'а есть специальный класс Bundle,
                    // кторый хранит пары ключ-значение (как хеш таблица)

                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                // "обёртка" для меню отправки
                startActivity(shareIntent)
                viewModel.clickingShareById(post.id)

            }

            override fun onEye(post: Post) {
                viewModel.clickingEyeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                newPostLauncher.launch(post.content)
            }

            override fun onImageClick(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.link))
                startActivity(intent)
            }

            override fun onPlayClick(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.link))
                startActivity(intent)
            }
        })

        mainBinding.recyclerList.adapter = adapter

        viewModel.data.observe(this) {
            val thereIsAnewPost = it.size > adapter.currentList.size
            adapter.submitList(it) {
                if (thereIsAnewPost) {
                    mainBinding.recyclerList.smoothScrollToPosition(0)
                }
            }


        }


    }


}

// mainBinding.send.setOnClickListener {
//            with(mainBinding.content) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_content),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//
//                setText(context.getString(R.string.empty_string))
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }


//        viewModel.edited.observe(this) {
//            if (it.id == 0L) {
//                mainBinding.group.visibility = View.GONE
//                return@observe
//            }
//
//            mainBinding.group.visibility = View.VISIBLE
//            with(mainBinding.content) {
//                mainBinding.editableMessage.text = it.content
//                requestFocus()
//                setText(it.content)
//            }
//
//        }
//
//
//        mainBinding.icCross.setOnClickListener {
//            viewModel.editedIsEmpty()
//            with(mainBinding.content) {
//                setText(context.getString(R.string.empty_string))
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }


//            root.setOnClickListener {
//                Log.d("stuff", "root")
//            }
//
//            avatar.setOnClickListener {
//                Log.d("stuff", "avatar")
//            }
//                Log.d("stuff", "like")
