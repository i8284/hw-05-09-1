package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias LikeClickListener = (post: Post) -> Unit
typealias ShareClickListener = (post: Post) -> Unit


class PostsAdapter(
    private val likeClickListener: LikeClickListener,
    private val shareClickListener: ShareClickListener
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeClickListener, shareClickListener)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }


}