package com.fabledt5.reddit.data.remote

import com.fabledt5.reddit.data.remote.dto.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET(value = "/r/android/hot.json")
    suspend fun getRedditPosts(
        @Query(value = "limit") loadSize: Int,
        @Query(value = "before") before: String?,
        @Query(value = "after") after: String?
    ) : RedditResponse

}