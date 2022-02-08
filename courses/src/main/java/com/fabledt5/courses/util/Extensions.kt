package com.fabledt5.courses.util

import android.graphics.drawable.GradientDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import com.fabledt5.courses.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenStarted(lifeCycleScope: LifecycleCoroutineScope) = lifeCycleScope
    .launchWhenStarted {
        this@launchWhenStarted.collect()
    }

fun ConstraintLayout.setPracticeGradientBackground() {
    this.background = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
            ContextCompat.getColor(context, R.color.SpringGreen),
            ContextCompat.getColor(context, R.color.DarkTurquoise)
        )
    )
}

fun ConstraintLayout.setLabWorkGradientBackground() {
    this.background = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
            ContextCompat.getColor(context, R.color.DarkOrange),
            ContextCompat.getColor(context, R.color.Oranged)
        )
    )
}

fun Long.toDaysDifference() = (this / (1000 * 60 * 60 * 24)) % 365

fun Long.toHoursDifference() = (this / (1000 * 60 * 60)) % 24

fun Long.toMinutesDifference() = (this / (1000 * 60)) % 60