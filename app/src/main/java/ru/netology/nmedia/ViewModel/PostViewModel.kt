package ru.netology.nmedia.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Impl.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.SingleLiveEvent


class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data
    //val data get() = repository.data  - тоже самое

    val sharePostContent = SingleLiveEvent<Post>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String?>()

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
        navigateToPostContentScreenEvent.value =
            post.content // отобразится контент текущего поста на экране
    }

    override fun onPlayVideoClicked(post: Post) {
        val url = requireNotNull(post.video) {
            "Url is null"
        }
        playVideo.value = url
    }

    //endregion
}