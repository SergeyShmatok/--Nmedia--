package ru.netology.nmedia._Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia._Activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appBinding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(appBinding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(
                    appBinding.root,
                    R.string.error_empty_content2,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    finish()
                }.show()
                return@let
            }

            findNavController(R.id.navController).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply { textArg = text }
            )

        }

    }
}