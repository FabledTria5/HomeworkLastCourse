package com.fabledt5.reddit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fabledt5.reddit.data.local.dao.RedditPostsDao
import com.fabledt5.reddit.data.local.dao.RemoteKeysDao
import com.fabledt5.reddit.data.local.entities.RedditPostEntity
import com.fabledt5.reddit.data.local.entities.RemoteKeyEntity

@Database(
    entities = [RedditPostEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun postsDao(): RedditPostsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}