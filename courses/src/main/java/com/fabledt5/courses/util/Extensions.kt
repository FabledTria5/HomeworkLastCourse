package com.fabledt5.courses.util

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenStarted(lifeCycleScope: LifecycleCoroutineScope) = lifeCycleScope
    .launchWhenStarted {
        this@launchWhenStarted.collect()
    }

fun Long.toDaysDifference() = (this / (1000 * 60 * 60 * 24)) % 365

fun Long.toHoursDifference() = (this / (1000 * 60 * 60)) % 24

fun Long.toMinutesDifference() = (this / (1000 * 60)) % 60