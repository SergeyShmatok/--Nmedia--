package ru.netology.nmedia

// here are the data-classes: Posts

data class Post(
    val id: Long = 0,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 999,
    val shares: Int = 1699,
    val views: Int = 1_099_999,
    val likedByMe: Boolean = true,
)

    var postNetoGreeting = Post(
    id = 1,
    author = "Нетология. Университет интернет-профессий будущего",
    content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
    published = "21 мая в 18:36",
    likedByMe = false
)

