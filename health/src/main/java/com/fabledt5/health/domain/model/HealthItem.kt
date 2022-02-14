package com.fabledt5.health.domain.model

data class HealthItem(
    val dateAdded: String,
    val timeAdded: String,
    val lowPressure: String,
    val highPressure: String,
    val pulse: String
)
