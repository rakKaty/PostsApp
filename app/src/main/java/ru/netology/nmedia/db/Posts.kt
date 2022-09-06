package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    likedByMe =  likedByMe,
    shares = shares,
    video = video
)


internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    likedByMe =  likedByMe,
    shares = shares,
    video = video
)