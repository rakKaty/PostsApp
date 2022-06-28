package ru.netology.nmedia.data.Impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        List(10) { index ->
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
}