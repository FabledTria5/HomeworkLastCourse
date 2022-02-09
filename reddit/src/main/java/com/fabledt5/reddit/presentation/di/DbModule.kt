package com.fabledt5.reddit.presentation.di

import android.content.Context
import androidx.room.Room
import com.fabledt5.reddit.data.local.RedditDatabase
import com.fabledt5.reddit.data.local.dao.RedditPostsDao
import com.fabledt5.reddit.data.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RedditDatabase = Room
        .databaseBuilder(context, RedditDatabase::class.java, "reddit_database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providePostsDao(redditDatabase: RedditDatabase): RedditPostsDao = redditDatabase.postsDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(redditDatabase: RedditDatabase): RemoteKeysDao =
        redditDatabase.remoteKeysDao()

}