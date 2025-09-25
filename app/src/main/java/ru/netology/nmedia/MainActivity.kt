package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import formatCount
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets(binding.root)


        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            10000,
            100
        )

        with(binding) {
            content.text = post.content
            author.text = post.author
            published.text = post.published
            views.text = formatCount(post.viewsCount)
            shareCount.text = formatCount(post.sharesCount)


            if (post.likedByMe) {
                like?.setImageResource(R.drawable.baseline_favorite_24)

            }

            likesCount?.text = formatCount(post.likesCount)

            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.baseline_favorite_24
                    } else {
                        R.drawable.baseline_favorite_border_24
                    }
                )

                if (post.likedByMe) {
                    post.likesCount += 1
                } else {
                    post.likesCount -= 1
                }
                likesCount?.text = formatCount(post.likesCount)

            }

            share.setOnClickListener {
                post.sharesCount = post.sharesCount + 1
                shareCount.text = formatCount(post.sharesCount)
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