package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "posts")
class PostEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    val id: Long,
    @ColumnInfo(name= "author")
    val author: String,
    @ColumnInfo(name= "content")
    val content: String,
    @ColumnInfo(name= "published")
    val published: String,
    @ColumnInfo(name= "likes")
    var likes: Int = 0,
    @ColumnInfo(name= "likedByMe")
    val likedByMe: Boolean = false,
    @ColumnInfo(name= "shares")
    var shares: Int = 999,
    @ColumnInfo(name= "video")
    val video: String?
)