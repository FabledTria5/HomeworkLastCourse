package com.fabledt5.reddit.domain.use_cases

import com.fabledt5.reddit.domain.repository.RedditRepository
import javax.inject.Inject

class GetRedditPosts @Inject constructor(private val redditRepository: RedditRepository) {

    operator fun invoke() = redditRepository.getRedditPosts()

}