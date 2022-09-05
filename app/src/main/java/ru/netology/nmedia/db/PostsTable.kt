package ru.netology.nmedia.db

object PostsTable {

    const val NAME = "Posts"

    val DDL= """
        CREATE TABLE $NAME (
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
        ${Column.CONTENT.columnName} TEXT NOT NULL,
        ${Column.PUBLISHED.columnName} TEXT NOT NULL,
        ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
        ${Column.SHARES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.VIDEO.columnName} TEXT
        )
    """.trimIndent()

    val ALL_COLUMNS_NAMES = Column.values().map { it.columnName }.toTypedArray()

    enum class Column(val columnName: String) {
        ID ("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKES("likes"),
        LIKED_BY_ME("likedByMe"),
        SHARES("shares"),
        VIDEO("video")
    }
}