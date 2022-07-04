package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter

import ru.netology.nmedia.databinding.ActivityMainBinding

import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.util.hideKeyboard

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }


        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                //binding.groupEditCancel.visibility = View.GONE
            }
        }

        binding.cancelEditButton.setOnClickListener {
            with(binding.contentEditText) {
                text = null
                clearFocus()
                hideKeyboard()
                binding.groupEditCancel.visibility = View.GONE
            }
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                setText(currentPost?.content)

                if (currentPost?.content != null) {
                    requestFocus()
                    binding.groupEditCancel.visibility = View.VISIBLE
                } else {
                    clearFocus()
                    hideKeyboard()
                    binding.groupEditCancel.visibility = View.GONE
                }
            }
        }
    }
}