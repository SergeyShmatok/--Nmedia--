package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PostRepositoryFun {
    fun get(): LiveData<Post>
    fun like()
    fun clickOnShare()
    fun clickOnEye ()

}

class PostRepositoryModel : PostRepositoryFun {

    private var postNetoGreeting = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likedByMe = false
    )
    private val data = MutableLiveData(postNetoGreeting)

    override fun get(): LiveData<Post> = data
    override fun like() {

        postNetoGreeting = postNetoGreeting.copy(likedByMe = !postNetoGreeting.likedByMe)
        postNetoGreeting =
            if (postNetoGreeting.likedByMe) postNetoGreeting.copy(likes = postNetoGreeting.likes + 1) else
                postNetoGreeting.copy(likes = postNetoGreeting.likes - 1)
        data.value = postNetoGreeting
    }

    override fun clickOnShare() {
        postNetoGreeting = postNetoGreeting.copy(shares = postNetoGreeting.shares + 1)
        data.value = postNetoGreeting

    }

    override fun clickOnEye() {
        postNetoGreeting = postNetoGreeting.copy(views = postNetoGreeting.views + 1)
        data.value = postNetoGreeting
    }
}
