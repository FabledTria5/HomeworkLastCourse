package com.fabledt5.drivingcar

import android.graphics.PointF
import android.view.View
import kotlin.math.abs
import kotlin.math.atan2

class Car(private val car: View, private val screenWidth: Float, private val screenHeight: Float) {

    enum class TrackDirections {
        TO_BOTTOM {
            override fun next() = TO_TOP
        },
        TO_TOP {
            override fun next() = TO_BOTTOM
        };

        abstract fun next(): TrackDirections
    }

    companion object {
        private const val CAR_DRIVE_ANIMATION_DURATION = 700L
        private const val CAR_ROTATION_ANIMATION_DURATION = 200L
    }

    private var currentCarDirection = TrackDirections.TO_BOTTOM

    fun drive() {
        val verticalPointsToTarget = (3..5).random()
        rotateCar(verticalPointsToTarget)
    }

    private fun rotateCar(pointsToTarget: Int) {
        val xPoint = calculateXTranslation(pointsToTarget)
        val yPoint = calculateYTranslation(pointsToTarget)
        val rotationAngle = angleBetweenPoints(PointF(car.x, car.y), PointF(xPoint, yPoint))

        car.animate()
            .rotation(rotationAngle.toFloat())
            .setDuration(CAR_ROTATION_ANIMATION_DURATION)
            .withEndAction {
                movieCarToPoint(pointsToTarget, xPoint, yPoint)
            }
    }

    private fun movieCarToPoint(pointsToTarget: Int, xPoint: Float, yPoint: Float) {
        car.animate()
            .translationX(xPoint)
            .translationY(yPoint)
            .setDuration(CAR_DRIVE_ANIMATION_DURATION)
            .withEndAction {
                if (pointsToTarget > 0) rotateCar(pointsToTarget = pointsToTarget - 1)
                else currentCarDirection = currentCarDirection.next()
            }
    }

    private fun angleBetweenPoints(a: PointF, b: PointF): Double {
        val deltaY = abs(b.y - a.y)
        val deltaX = abs(b.x - a.x)
        val angle = Math.toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble()))
        return angle
    }

    private fun calculateXTranslation(pointsToTarget: Int) = when (currentCarDirection) {
        TrackDirections.TO_BOTTOM -> {
            if (pointsToTarget > 1) (0..screenWidth.toInt()).random().toFloat()
            else screenWidth
        }
        TrackDirections.TO_TOP -> {
            if (pointsToTarget > 1) (0..screenWidth.toInt()).random().toFloat() else 0f
        }
    }

    private fun calculateYTranslation(pointsToTarget: Int) =
        when (currentCarDirection) {
            TrackDirections.TO_BOTTOM -> {
                if (pointsToTarget > 1) (0..screenHeight.toInt()).random().toFloat()
                else screenHeight
            }
            TrackDirections.TO_TOP -> {
                if (pointsToTarget > 1) (0..screenHeight.toInt()).random().toFloat()
                else 0f
            }
        }
}