package com.fabledt5.reddit.data.repository

import androidx.paging.*
import com.fabledt5.reddit.data.local.dao.RedditPostsDao
import com.fabledt5.reddit.data.local.toDomain
import com.fabledt5.reddit.data.paging.RedditRemoteMediator
import com.fabledt5.reddit.domain.repository.RedditRepository
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
@BoundTo(RedditRepository::class, component = SingletonComponent::class)
class RedditRepositoryImpl @Inject constructor(
    private val redditRemoteMediator: RedditRemoteMediator,
    private val postsDao: RedditPostsDao
) : RedditRepository {

    override fun getRedditPosts() =
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 40, prefetchDistance = 3),
            remoteMediator = redditRemoteMediator
        ) {
            postsDao.getPosts()
        }.flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toDomain()
            }
        }
}