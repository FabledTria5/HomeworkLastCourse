package com.fabledt5.reddit.data.local

import com.fabledt5.reddit.data.local.entities.RedditPostEntity
import com.fabledt5.reddit.domain.model.RedditPost

fun RedditPostEntity.toDomain(): RedditPost =
    RedditPost(postTitle = postTitle, postRating = postRating, postCommentsCount = postComments)