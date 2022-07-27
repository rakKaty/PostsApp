package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.IntentHandlerActivityBinding

class IntentHandlerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = IntentHandlerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        if (intent.action != Intent.ACTION_SEND) return

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (text.isNullOrBlank()) {

            Snackbar.make(
                binding.root,
                "Присланный текст пустой",
                Snackbar.LENGTH_INDEFINITE
            ).setAction(android.R.string.ok) {
                finish()
            }.show()
        } else {
            binding.root.text = text
        }
    }
}
