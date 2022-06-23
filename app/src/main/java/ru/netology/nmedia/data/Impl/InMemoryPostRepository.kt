package ru.netology.nmedia.data.Impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 0L,
            author = "Katya",
            content = "Events",
            published = "16.06.2022",
            likedByMe = false
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value){
            "Data value should not be null!"
        }
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe
        )

        data.value = likedPost
    }


    override fun share() {
        val currentPost = checkNotNull(data.value){
            "Data value should not be null!"
        }
        val sharedPost = currentPost.copy(
            shares = currentPost.shares + 1
        )

        data.value = sharedPost
    }
}