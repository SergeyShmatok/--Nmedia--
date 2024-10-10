package ru.netology.nmedia._Activity

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia._Activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentBlankBinding
import ru.netology.nmedia.other_date_and_service.LongArg
import ru.netology.nmedia.other_date_and_service.clicksInVkFormat

class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val blankBinding = FragmentBlankBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        val postId = arguments?.longArg

        viewModel.data.observe(viewLifecycleOwner) { posts ->

            posts.forEach { post ->
                if (post.id == postId)

                    with(blankBinding) {

                        author.text = post.author
                        published.text = post.published
                        content.text = post.content
                        icLikes.text = clicksInVkFormat(post.likes)
                        icShares.text = clicksInVkFormat(post.shares)
                        icEye.text = clicksInVkFormat(post.views)

                        icLikes.isChecked = post.likedByMe

                        icLikes.setOnClickListener {
                            viewModel.likeById(post.id)
                        }
                        icShares.setOnClickListener {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, post.content)

                            }
                            val shareIntent =
                                Intent.createChooser(intent, getString(R.string.chooser_share_post))
                            startActivity(shareIntent)
                            viewModel.clickingShareById(post.id)
                        }

                        icEye.setOnClickListener {
                            viewModel.clickingEyeById(post.id)
                        }

                        icMenu.setOnClickListener { view ->
                            PopupMenu(view.context, view).apply {
                                inflate(R.menu.popup_post_menu)
                                setOnMenuItemClickListener { item ->
                                    when (item.itemId) {

                                        R.id.remove -> {
                                            findNavController().navigateUp()
                                            viewModel.removeById(post.id)
                                            true
                                        }

                                        R.id.edit -> {
                                            viewModel.edit(post)
                                            findNavController().navigate(R.id.action_blankFragment_to_newPostFragment,
                                                Bundle().apply { textArg = post.content }
                                            )
                                            true
                                        }

                                        else -> false

                                    }

                                }

                            }.show()
                        }

                    }
            }

        }


        return blankBinding.root
    }


    companion object {

        var Bundle.longArg: Long by LongArg

    }

}
