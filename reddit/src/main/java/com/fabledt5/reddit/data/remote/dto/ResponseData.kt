package com.fabledt5.reddit.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("after")
    val after: String?,
    @SerializedName("before")
    val before: String?,
    @SerializedName("children")
    val children: List<Children>
)