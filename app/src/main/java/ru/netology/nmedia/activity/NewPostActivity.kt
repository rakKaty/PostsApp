package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.number.NumberFormatter
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // обращаемся к intent, с которым Активити была запущена, и достаем данные, переданные через контракт
        val textToEdit = intent?.extras?.getString(Intent.EXTRA_TEXT) ?: ""
        binding.edit.setText(textToEdit)
        binding.edit.requestFocus()


        binding.ok.setOnClickListener {
            val resultIntent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                // если в поле ввода текста пусто, то рез-т выполнения активити отменить
                // и вкладываем пустой интент
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                // если все хорошо, то конвертируем текст в строку, укладываем в заготовленный интент
                val content = binding.edit.text.toString()
                resultIntent.putExtra(POST_CONTENT_EXTRA_KEY, content)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
    }



    private companion object {
        const val POST_CONTENT_EXTRA_KEY = "postContent"
    }

    object ResultContract : ActivityResultContract<String?, String?>() {

        override fun createIntent(context: Context, input: String?) =
            Intent(context, NewPostActivity::class.java).putExtra(Intent.EXTRA_TEXT, input)

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null
            return intent.getStringExtra(POST_CONTENT_EXTRA_KEY)
        }
    }


}