package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.SingleLiveEvent


class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = FilePostRepository(application)

    val data by repository::data
    //val data get() = repository.data  - тоже самое

    val sharePostContent = SingleLiveEvent<Post>()
    val navigateToPostContentScreen = SingleLiveEvent<String?>()
    val navigateToPostScreen = SingleLiveEvent<Long>()

    /**
     * Значение события содержит url видео для воспроизведения
     */
    val playVideo = SingleLiveEvent<String>()

    private val currentPost = MutableLiveData<Post?>(null)

    override fun onCreateNewPost(newPostContent: String) {
        if (newPostContent.isBlank()) return
        val post = currentPost.value?.copy(
            content = newPostContent
        ) ?: Post( // если current.value null, то мы в режиме создания поста, а не редактирования
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = newPostContent,
            published = "Today"
        )
        repository.save(post)
        currentPost.value = null
    }


    //region PostInteractionListener
    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        sharePostContent.value = post
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post // закидываем пост в поток
        navigateToPostContentScreen.value =
            post.content // отобразится контент текущего поста на экране
    }

    override fun onPlayVideoClicked(post: Post) {
        val url = requireNotNull(post.video) {
            "Url is null"
        }
        playVideo.value = url
    }

    override fun onPostClicked(id: Long) {
        navigateToPostScreen.value = id
    }

    //endregion
}