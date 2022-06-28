package ru.netology.nmedia.data.Impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import kotlin.properties.Delegates

internal class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likesIcon.setOnClickListener { onLikeClicked(post) }
            binding.shareIcon.setOnClickListener { onShareClicked(post) }
        }

        fun bind(post: Post): Unit {
            this.post = post

            with(binding) {
                authorName.text = post.author
                postText.text = post.content
                date.text = post.published
                likesIcon.setImageResource(getLikeIconResId(post.likedByMe))
                amountOfLikes.text = changeFormatOfNumberToText(getAmountOfLikes(post))

                amountOfShares.text = changeFormatOfNumberToText(post.shares)
            }
        }

        private fun getAmountOfLikes(post: Post) =
            if (post.likedByMe) {
                post.likes + 1
            } else post.likes


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}
