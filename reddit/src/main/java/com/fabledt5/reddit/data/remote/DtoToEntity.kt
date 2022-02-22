package com.fabledt5.reddit.data.remote

import com.fabledt5.reddit.data.local.entities.RedditPostEntity
import com.fabledt5.reddit.data.remote.dto.Children

fun List<Children>.toDomain(): List<RedditPostEntity> = map { child ->
    RedditPostEntity(
        postTitle = child.childData.title,
        postRating = child.childData.score,
        postComments = child.childData.numComments
    )
}