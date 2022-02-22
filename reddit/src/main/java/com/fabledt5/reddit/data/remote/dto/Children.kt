package com.fabledt5.reddit.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("data")
    val childData: ChildrenData,
)