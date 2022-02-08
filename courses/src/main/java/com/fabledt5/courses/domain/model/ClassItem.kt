package com.fabledt5.courses.domain.model

import java.util.*

data class ClassItem(
    val className: String,
    val teacherName: String,
    val classTime: String,
    val classDate: Date,
    val classType: ClassType,
    val isRunning: Boolean
)