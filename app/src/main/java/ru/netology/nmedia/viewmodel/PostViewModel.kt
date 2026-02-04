package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInFileImpl

private val empty = Post(
    id = 0L,
    author = "",
    published = "",
    content = "",
    viewsCount = 0,
    sharesCount = 0,
    likedByMe = false,
    likesCount = 0,
    video = null
)
class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryInFileImpl(application)

    val edited = MutableLiveData(empty)


    fun get(): LiveData<List<Post>> = repository.get()

    fun likeById(id: Long) {
        repository.likeById(id)
    }
    fun shareById(id: Long) {
        repository.shareById(id)
    }
    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun save(newContent: String) {
        edited.value?.let { post ->
            if (post.content != newContent) {
                repository.save(post.copy(content = newContent))
            }
        }
        edited.value = empty
    }

    fun cancelEdit() {
        edited.value = empty
    }

}