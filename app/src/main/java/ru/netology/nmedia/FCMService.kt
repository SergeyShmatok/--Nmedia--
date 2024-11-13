package ru.netology.nmedia

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia._Activity.AppActivity

class FCMService: FirebaseMessagingService() {

    private val gson = Gson()

    private val action1 = "action_1"
    private val action2 = "action_2"
    private val content1 = "content_1"
    private val content2 = "content_2"
    private val channelId = "test channel"

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // проверка на верcию Android 8.0
            // начиная с этой версии все нотификации должны быть присоединены к каналам
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel) // регистрация канала через системную службу
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {

        message.data[action1]?.let {

            when (it) {
                Action.LIKE.toString() -> handleLike(gson.fromJson(message.data[content1], Like::class.java))
            }

            }

            message.data[action2]?.let {
                when (it) {
                Action.NewPost.toString() -> handlePost(gson.fromJson(message.data[content2], Post::class.java))
                }

            }

}

private fun pendingIntent (): PendingIntent {

    // Create an Intent for the activity you want to start.
     val resultIntent = Intent(this, AppActivity::class.java) // ссылка на класс, который нужно запустить

     val resultPendingIntent = TaskStackBuilder.create(this).run { // PendingIntent - это 'обёртка'
        // Add the intent, which inflates the back stack.
        addNextIntentWithParentStack(resultIntent)
        // Get the PendingIntent containing the entire back stack.
        getPendingIntent(0,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    return resultPendingIntent
}





    private fun handleLike(like: Like) {

        val notification = Builder(this, channelId) // создание уведомления
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_user_liked, like.userName, like.postAuthor))
            .setContentIntent(pendingIntent())
            .setStyle(BigTextStyle())
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        } // есть ли у приложения разрешение на показ уведомления

        NotificationManagerCompat.from(this).notify(1, notification) // если есть - показ уведомления
    }



    private fun handlePost(post: Post) {

        val notification = Builder(this, channelId) // создание уведомления
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_user_published_post, post.userName, post.postContent))
            .setContentIntent(pendingIntent())
            .setStyle(BigTextStyle().bigText(post.postContent))
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(this).notify(2, notification)
    }

        override fun onNewToken(token: String) {
            println(token)
        }

}



enum class Action {
    LIKE, NewPost,
}


    data class Like (
        val userId: Long,
        val userName: String,
        val postId: Long,
        val postAuthor: String,

    )

data class Post (
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postContent: String
)



