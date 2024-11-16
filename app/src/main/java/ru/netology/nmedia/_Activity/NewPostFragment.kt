package ru.netology.nmedia._Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.other_date_and_utils.StringArg

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val newPostBinding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

// В случае когда пользователь покидает фрагмент нажатием системной кнопки "Назад"
// можно добавить соответствующий обработчик в onCreateView:
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            viewModel.run {
                arguments?.textArg ?: saveDraft(newPostBinding.edit.text.toString())
                editedIsEmpty()
            }

            findNavController().navigateUp() // обязательно повторить
        } // Если -onBackPressedDispatcher- был вызван, стандартный перход "назад" не работает.




        arguments?.textArg?.let { newPostBinding.edit.setText(it) }

        if (newPostBinding.edit.text.toString().isEmpty()) {
            newPostBinding.edit.setText(viewModel.getDraft())
        }

//        arguments?.textArg?.let(newPostBinding.edit::setText)

        newPostBinding.ok.setOnClickListener {
            val text = newPostBinding.edit.text.toString()

            if (text.isNotBlank()) {
                viewModel.run {
                    changeContent(text)
                    save()
                    dropDraft() // очистка черновика
                }
            }
            findNavController().navigateUp()

        }

        return newPostBinding.root
    }

    companion object {

        var Bundle.textArg: String? by StringArg

    }

}


//      newPostBinding.apply {
//            icAttach.setOnClickListener {
//                PopupMenu(it.context, it).apply {
//                    inflate(R.menu.new_post_attach_menu)
//                    setOnMenuItemClickListener { item ->
//                        when (item.itemId) {
//
//                            R.id.Video -> {
//                                true
//                            }
//
//                            else -> false
//                        }
//                    }
//
//                }.show()
//
//            }
//
//        }


//        val intent = intent.getStringExtra(KEY_TEXT)
//
//        intent?.let { newPostBinding.edit.setText(intent) }