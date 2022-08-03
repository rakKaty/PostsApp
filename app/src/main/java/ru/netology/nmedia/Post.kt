package ru.netology.nmedia

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    val likedByMe: Boolean = false,
    var shares: Int = 999,
    val video: String? = "https://www.youtube.com/watch?v=boO-OdBnTic"
)
