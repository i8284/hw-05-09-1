package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import formatCount
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets(binding.root)
        bindToViewModel(binding)
        setupListeners(binding)

    }

    private fun setupListeners(binding: ActivityMainBinding) {
        binding.like?.setOnClickListener {
            viewModel.like()
        }
        binding.share?.setOnClickListener {
            viewModel.share()
        }
    }

    private fun bindToViewModel(binding: ActivityMainBinding) {
        viewModel.get().observe(this) { post ->

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
            }
        }
    }

    private fun applyInsets(root: View) {
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}