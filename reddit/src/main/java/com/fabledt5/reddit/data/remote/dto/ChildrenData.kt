package com.fabledt5.reddit.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChildrenData(
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("score")
    val score: Int,
    @SerializedName("title")
    val title: String
)