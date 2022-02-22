package com.fabledt5.reddit.domain.repository

import androidx.paging.PagingData
import com.fabledt5.reddit.domain.model.RedditPost
import kotlinx.coroutines.flow.Flow

interface RedditRepository {
    fun getRedditPosts(): Flow<PagingData<RedditPost>>
}