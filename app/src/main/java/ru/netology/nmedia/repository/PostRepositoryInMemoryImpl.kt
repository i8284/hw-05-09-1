package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import formatCount
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {

    private val defaultPosts = List(20) { counter ->
        Post(
            counter + 1L,
            "Нетология. Университет интернет-профессий будущего",
            "22 мая в 18:36",
            "Post №${counter} Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            10000,
            100
        )
    }

    private var nextId = defaultPosts.first().id + 1
    private val data = MutableLiveData(defaultPosts)

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        val posts = data.value.orEmpty()
        val newPosts = posts.map { post ->
            if (post.id == id) {
                val newLikedByMe = !post.likedByMe
                val newLikesCount = if (newLikedByMe) post.likesCount + 1 else post.likesCount - 1
                post.copy(
                    likedByMe = newLikedByMe,
                    likesCount = newLikesCount
                )
            } else {
                post
            }
        }
        data.value = newPosts

    }

    override fun shareById(id: Long) {
        val posts = data.value.orEmpty()
        val newPosts = posts.map { post ->
            if (post.id == id) {
                val newSharesCount = post.sharesCount + 1
                post.copy(
                    sharesCount = newSharesCount
                )
            } else {
                post
            }
        }

        data.value = newPosts

    }

    override fun removeById(id: Long) {
        data.value = data.value?.filter { it.id != id }
    }

    override fun save(post: Post) {
        if(post.id == 0L) {
            data.value = listOf(
                post.copy(
                    id=nextId++,
                    author = "Me",
                    published = "Now",

                )
            ) + data.value.orEmpty()
        } else {
            data.value = data.value?.map {
                if (it.id == post.id) {
                    post
                } else {
                    it
                }
            }
        }
    }

}