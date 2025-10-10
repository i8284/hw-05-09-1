package ru.netology.nmedia.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import formatCount
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeClickListener: LikeClickListener,
    private val shareClickListener: ShareClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            content.text = post.content
            author.text = post.author
            published.text = post.published
            views.text = formatCount(post.viewsCount)
            shareCount.text = formatCount(post.sharesCount)
            likesCount?.text = formatCount(post.likesCount)

            like.setImageResource(
                if (post.likedByMe) {
                    R.drawable.baseline_favorite_24
                } else {
                    R.drawable.baseline_favorite_border_24
                }
            )

            likesCount.isVisible = (post.likesCount > 0)

            like?.setOnClickListener {
                likeClickListener(post)
            }
            share?.setOnClickListener {
                shareClickListener(post)
                //viewModel.shareById(post.id)
            }
        }
    }
}