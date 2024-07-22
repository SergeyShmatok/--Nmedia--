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
