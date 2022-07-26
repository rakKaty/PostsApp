package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.activity.NewPostActivity
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


        viewModel.sharePostContent.observe(this) { post ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(
                intent, getString(R.string.chooser_share_post)
            )
            startActivity(shareIntent)
        }


        viewModel.playVideo.observe(this) { url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }


        val activityLauncher = registerForActivityResult(
            NewPostActivity.ResultContract
        ) { postContent: String? ->
            postContent ?: return@registerForActivityResult
            viewModel.onCreateNewPost(postContent)
            // postContent.let(viewModel::onCreateNewPost)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) { postContent ->
            activityLauncher.launch(postContent)
        }


        binding.fab.setOnClickListener {
            activityLauncher.launch("")
        }
    }
}