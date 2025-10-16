package ru.netology.nmedia.adapter

import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import formatCount
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener
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
                listener.onLike(post)
            }
            share?.setOnClickListener {
                listener.onShare(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }

                    show()
                }
            }
        }
    }
}