package ru.netology.nmedia.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.utils.AndroidUtils

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val existingContent = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
        binding.edit.setText(existingContent)
        binding.edit.setSelection(existingContent.length)

        AndroidUtils.showKeyboard(binding.edit)

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val intent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
}

class EditPostContract : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, EditPostActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(Intent.EXTRA_TEXT)

    }
}
