package com.fabledt5.reddit.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class RedditPostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "post_title")
    val postTitle: String,
    @ColumnInfo(name = "post_rating")
    val postRating: Int,
    @ColumnInfo(name = "post_comments")
    val postComments: Int
)
