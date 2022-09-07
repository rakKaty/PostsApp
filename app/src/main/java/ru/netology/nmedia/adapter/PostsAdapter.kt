package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding


internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(DiffCallback) {


    class PostViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likesIcon.setOnClickListener { listener.onLikeClicked(post) }
            binding.shareIcon.setOnClickListener { listener.onShareClicked(post) }
            binding.videoBanner.setOnClickListener { listener.onPlayVideoClicked(post) }
            binding.options.setOnClickListener { popupMenu.show() }
        }

        init {
            binding.postText.setOnClickListener { listener.onPostClicked(post.id) }
            binding.authorName.setOnClickListener { listener.onPostClicked(post.id) }
            binding.date.setOnClickListener { listener.onPostClicked(post.id) }
            binding.avatar.setOnClickListener { listener.onPostClicked(post.id) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                postText.text = post.content
                date.text = post.published
                likesIcon.isChecked = post.likedByMe
                likesIcon.text = changeFormatOfNumberToText(post.likes)
                shareIcon.text = changeFormatOfNumberToText(post.shares)
                videoGroup.isVisible = post.video != null
            }
        }

        private fun changeFormatOfNumberToText(number: Int): String = when {
            number < 1000 -> "$number"
            number in 1000..9999 -> "${number / 1000}.${(number % 1000) / 100}K"
            number in 10_000..999_999 -> "${number / 1000}K"
            number in 1_000_000..9_999_999 -> "${number / 1_000_000}.${(number % 1_000_000) / 100}M"
            else -> "${number / 1_000_000}M"
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}
