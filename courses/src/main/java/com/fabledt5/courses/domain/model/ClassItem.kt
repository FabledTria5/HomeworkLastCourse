package com.fabledt5.courses.domain.model

import java.util.*

data class ClassItem(
    val className: String = "",
    val teacherName: String = "",
    val classTime: String = "00:00 - 00:00",
    val classDate: Date = Date(),
    val classType: ClassType = ClassType.Lecture,
    val isRunning: Boolean = false
)