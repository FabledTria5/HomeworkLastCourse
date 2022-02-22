package com.fabledt5.reddit.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.fabledt5.reddit.data.local.RedditDatabase
import com.fabledt5.reddit.data.local.dao.RedditPostsDao
import com.fabledt5.reddit.data.local.dao.RemoteKeysDao
import com.fabledt5.reddit.data.local.entities.RedditPostEntity
import com.fabledt5.reddit.data.local.entities.RemoteKeyEntity
import com.fabledt5.reddit.data.remote.RedditApi
import com.fabledt5.reddit.data.remote.toDomain
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@ExperimentalPagingApi
class RedditRemoteMediator @Inject constructor(
    private val redditApi: RedditApi,
    private val redditDatabase: RedditDatabase,
    private val postsDao: RedditPostsDao,
    private val remoteKeysDao: RemoteKeysDao
) : RemoteMediator<Int, RedditPostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RedditPostEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getRemoteKeys()
                }
            }

            val redditResponse = redditApi.getRedditPosts(
                loadSize = state.config.initialLoadSize,
                before = loadKey?.before,
                after = loadKey?.after
            )

            val responseData = redditResponse.responseData
            val redditPosts = responseData.children.toDomain()

            if (redditPosts.isNotEmpty()) {
                redditDatabase.withTransaction {
                    remoteKeysDao.addKeys(
                        RemoteKeyEntity(
                            before = responseData.before,
                            after = responseData.after
                        )
                    )
                    postsDao.insertAll(redditPosts = redditPosts)
                }
            }

            MediatorResult.Success(endOfPaginationReached = responseData.after == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeys(): RemoteKeyEntity? {
        return remoteKeysDao.getRemoteKeys().firstOrNull()
    }

}