package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import java.lang.reflect.Type

class PostRepositoryInFileImpl(
    private val context: Context
): PostRepository {

//    private val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private var posts: List<Post> = getPosts()
        set(value) {
            field = value
            sync()
        }


    private var nextId = getId()
    private val data = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {


        posts = posts.map { post ->
            if (post.id == id) {
                val newLikedByMe = !post.likedByMe
                val newLikesCount = if (newLikedByMe) post.likesCount + 1 else post.likesCount - 1
                post.copy(
                    likedByMe = newLikedByMe,
                    likesCount = newLikesCount
                )
            } else post
        }
        data.value = posts

    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(sharesCount = post.sharesCount + 1)
            } else post
        }
        data.value = posts

    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {

        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    published = "Now",
                )
            ) + posts
        } else {
            posts.map {
                if (it.id == post.id) post else it
            }
        }
        data.value = posts
    }

    private fun getPosts(): List<Post> = context.filesDir.resolve(FILE_NAME)
        .takeIf {
            it.exists()
        }
        ?.inputStream()
        ?.bufferedReader()
        ?.use {
            gson.fromJson(it, postsType)

        } ?: emptyList()
//        prefs.getString(POSTS_KEY, null)?.let {
//            gson.fromJson<List<Post>>(it, postsType)
//        } ?: emptyList()

    private fun getId() = (posts.maxByOrNull { it.id }?.id ?: 0L) + 1L
//        prefs.getLong(ID_KEY, 1L)



    private fun sync() {
//        prefs.edit {
//            putString(POSTS_KEY, gson.toJson(posts))
//            putLong(ID_KEY, nextId)
//        }
        context.filesDir.resolve(FILE_NAME).outputStream().bufferedWriter()
            .use {
                it.write(gson.toJson(posts))
            }
    }


    private companion object {
        const val FILE_NAME = "posts.json"
//        const val ID_KEY = "nextId"
        val gson = Gson()
        val postsType: Type = object : TypeToken<List<Post>>() {}.type
    }

}