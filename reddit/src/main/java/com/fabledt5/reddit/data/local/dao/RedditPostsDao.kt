package com.fabledt5.reddit.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fabledt5.reddit.data.local.entities.RedditPostEntity

@Dao
interface RedditPostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(redditPosts: List<RedditPostEntity>)

    @Query("SELECT * FROM posts_table")
    fun getPosts(): PagingSource<Int, RedditPostEntity>

    @Query("DELETE FROM posts_table")
    suspend fun clearPostsTable()

}