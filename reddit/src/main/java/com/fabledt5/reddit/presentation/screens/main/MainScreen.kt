package com.fabledt5.reddit.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.fabledt5.reddit.R
import com.fabledt5.reddit.domain.model.RedditPost
import com.fabledt5.reddit.presentation.theme.Orange

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val redditPosts = mainViewModel.redditPosts.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 10.dp, top = 10.dp, end = 10.dp)
    ) {
        items(redditPosts) { item ->
            item?.let {
                RedditPostItem(redditPost = it)
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Composable
fun RedditPostItem(redditPost: RedditPost) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = redditPost.postTitle)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.icon_star),
                    tint = Orange
                )
                Text(text = redditPost.postRating.toString())
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = stringResource(R.string.icon_comment),
                    tint = Color.Unspecified
                )
                Text(text = redditPost.postCommentsCount.toString())
            }
        }
    }
}