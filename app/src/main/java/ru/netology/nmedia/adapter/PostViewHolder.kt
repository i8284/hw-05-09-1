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
//          likesCount.isVisible = (post.likesCount > 0)
            like.isChecked = post.likedByMe
            like.text = formatCount(post.likesCount)
            share.text = formatCount(post.sharesCount)

            like?.setOnClickListener {
                listener.onLike(post)
            }
            share?.setOnClickListener {
                listener.onShare(post)
            }

            videoGroup.isVisible = !post.video.isNullOrBlank()

            videoContainer.setOnClickListener {
                listener.onVideo(post)
            }
            videoPlay.setOnClickListener {
                listener.onVideo(post)
            }

            menu.setOnClickListener { view ->
                val popupMenu = PopupMenu(view.context, view).apply {
                    inflate(R.menu.post_menu)

                    setOnMenuItemClickListener { item ->
                        val handled = when (item.itemId) {
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
                        if (handled) binding.menu.isChecked = false
                        handled
                    }

                    setOnDismissListener {
                        binding.menu.isChecked = false
                    }
                }

                popupMenu.show()
                binding.menu.isChecked = true
            }
        }
    }
}