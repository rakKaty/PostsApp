package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }



        binding.includePost.likesIcon.setOnClickListener {
            viewModel.onLikeClicked()
        }
        /*binding.includePost.likesIcon.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.includePost.likesIcon.setImageResource(getLikeIconResId(post.likedByMe))
            binding.includePost.amountOfLikes.text = changeFormatOfNumberToText(getAmountOfLikes(post))
        }*/

       binding.includePost.shareIcon.setOnClickListener {
           viewModel.onShareClicked()
        }
        /*binding.includePost.shareIcon.setOnClickListener {
            post.shares += 1
            binding.includePost.amountOfShares.text = changeFormatOfNumberToText(post.shares)
        } */
    }

    private fun ActivityMainBinding.render(post: Post) {
        includePost.authorName.text = post.author
        includePost.postText.text = post.content
        includePost.date.text = post.published
        includePost.likesIcon.setImageResource(getLikeIconResId(post.likedByMe))
        includePost.amountOfLikes.text = changeFormatOfNumberToText(getAmountOfLikes(post))
        includePost.amountOfShares.text = changeFormatOfNumberToText(post.shares)
    }


    private fun getAmountOfLikes(post: Post) =
        if (post.likedByMe) { post.likes + 1 } else post.likes


    private fun changeFormatOfNumberToText(number: Int): String = when {
        number < 1000 -> "$number";
        number in 1000..9999 -> "${number / 1000}.${(number % 1000) / 100}K"
        number in 10_000..999_999 -> "${number / 1000}K";
        number in 1_000_000..9_999_999 -> "${number / 1_000_000}.${(number % 1_000_000) / 100}M";
        else -> "${number / 1_000_000}M"
    }


    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp


}