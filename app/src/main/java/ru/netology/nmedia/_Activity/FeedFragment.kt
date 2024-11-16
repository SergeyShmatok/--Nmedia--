package ru.netology.nmedia._Activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.OnInteractionListener
import ru.netology.nmedia.other_date_and_utils.Post
import ru.netology.nmedia.PostAdapter
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia._Activity.BlankFragment.Companion.longArg
import ru.netology.nmedia._Activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val feedBinding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        feedBinding.plus.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

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
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content }
                )
            }

            override fun onScreenClick(post: Post) {
               findNavController().navigate(R.id.action_feedFragment_to_blankFragment,
                Bundle().apply { longArg = post.id }
               )
            }

        })

        feedBinding.recyclerList.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) {
            val thereIsAnewPost = it.size > adapter.currentList.size
            adapter.submitList(it) {
                if (thereIsAnewPost) {
                    feedBinding.recyclerList.smoothScrollToPosition(0)
                }
            }


        }

        return feedBinding.root
    }


}


//            override fun onImageClick(post: Post) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.link))
//                startActivity(intent)
//            }
//
//            override fun onPlayClick(post: Post) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.link))
//                startActivity(intent)
//            }

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


//        val newPostLauncher = registerForActivityResult(NewPostContract) {
//            it ?: let { viewModel.editedIsEmpty(); return@registerForActivityResult }
//            viewModel.run { changeContent(it); save() }
//        }