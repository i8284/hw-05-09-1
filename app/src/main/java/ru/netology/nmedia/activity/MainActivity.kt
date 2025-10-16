package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import formatCount
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets(binding.root)

        val adapter = PostsAdapter(
            object : PostListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)

                }

            }

        )
        binding.container.adapter = adapter
        viewModel.get().observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { edited ->
            if (edited.id != 0L) {
                AndroidUtils.hideKeyboard(binding.content)
                binding.contentEdit.text = edited.content
                binding.content.setText(edited.content)
                binding.editingGroup.visibility = View.VISIBLE
                binding.editingPanel.visibility = View.VISIBLE
                binding.cancelButton.setOnClickListener {
                    binding.content.setText("")
                    binding.content.clearFocus()
                    binding.editingGroup.visibility = View.INVISIBLE
                    binding.editingPanel.visibility = View.INVISIBLE
                    viewModel.cancelEdit()
                }
            }
        }



        binding.save.setOnClickListener {
            val currentText = binding.content.text?.trim()?.toString()
            if (currentText.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.error_empty_content), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.save(currentText)

            binding.content.setText("")
            binding.content.clearFocus()
            binding.editingGroup.visibility = View.INVISIBLE
            binding.editingPanel.visibility = View.INVISIBLE
            AndroidUtils.hideKeyboard(binding.content)

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