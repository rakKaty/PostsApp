package ru.netology.nmedia.data.Impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {


    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Some random content ${index + 1}",
                published = "23.06.2022",
                likedByMe = false
            )
        }
    )

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null!"
        }

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) it.copy(likedByMe = !it.likedByMe)
            else it
        }
    }


    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) it.copy(shares = it.shares + 1)
            else it
        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }


    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }
}