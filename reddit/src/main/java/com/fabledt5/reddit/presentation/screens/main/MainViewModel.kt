package com.fabledt5.reddit.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fabledt5.reddit.domain.model.RedditPost
import com.fabledt5.reddit.domain.use_cases.GetRedditPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(getRedditPosts: GetRedditPosts) : ViewModel() {

    val redditPosts = getRedditPosts().cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}