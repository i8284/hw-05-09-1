package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import formatCount
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {
    private val defaultPost = Post(
        1,
        "Нетология. Университет интернет-профессий будущего",
        "21 мая в 18:36",
        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        10000,
        100
    )

    private val data = MutableLiveData(defaultPost)

    override fun get(): LiveData<Post> = data

    override fun like() {
        val post = requireNotNull(data.value) { "post not initialized" }
        val newLikedByMe = !post.likedByMe
        val newLikesCount = if (newLikedByMe) post.likesCount + 1 else post.likesCount - 1
        data.value = post.copy(
            likedByMe = newLikedByMe,
            likesCount = newLikesCount
        )
    }

    override fun share() {
        val post = requireNotNull(data.value) { "post not initialized" }
        val newSharesCount = post.sharesCount + 1
        data.value = post.copy(
            sharesCount = newSharesCount
        )
    }
}