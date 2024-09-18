package ru.netology.nmedia._Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia._Activity.NewPostActivity.Companion.KEY_TEXT
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newPostBinding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(newPostBinding.root)

        val intent = intent.getStringExtra(KEY_TEXT)

        intent?.let { newPostBinding.edit.setText(intent) }

        newPostBinding.ok.setOnClickListener {
            val text = newPostBinding.edit.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                setResult(RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            }
            finish()
        }


    }


    companion object {
        const val KEY_TEXT = "editable_text"
    }


}


object NewPostContract : ActivityResultContract<String?, String?>() {

    override fun createIntent(context: Context, input: String?): Intent =
        Intent(context, NewPostActivity::class.java).putExtra(KEY_TEXT, input)

    override fun parseResult(resultCode: Int, intent: Intent?) =
        intent?.getStringExtra(Intent.EXTRA_TEXT)

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