package com.fabledt5.courses.data.db

import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.domain.model.ClassItem
import com.fabledt5.courses.domain.model.ClassType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

fun Flow<List<ClassEntity>>.toDomain(): Flow<List<ClassItem>> = map { list ->
    list.map { entity ->
        ClassItem(
            className = entity.className,
            teacherName = entity.teacherName,
            classTime = entity.classTime,
            classDate = getClassDate(entity.classTime, entity.classDate),
            isRunning = isClassRunning(entity.classTime),
            classType = getClassType(entity.classType),
        )
    }
}

fun getClassDate(classTime: String, classDate: String): Date {
    val classDateString = "$classDate $classTime"
    return SimpleDateFormat("dd MMM yyyy HH:mm - HH:mm", Locale("ru"))
        .parse(classDateString) ?: Date()
}

fun getClassType(classType: String) = when {
    classType.lowercase().contains("лекц") -> ClassType.Lecture
    classType.lowercase().contains("практ") -> ClassType.Practice
    classType.lowercase().contains("лаб") -> ClassType.LabWork
    else -> ClassType.Lecture
}

fun isClassRunning(classTime: String): Boolean {
    val localTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

    val startTime = classTime.substring(0, 5)
    val endTime = classTime.substring(
        classTime.length - 5,
        classTime.length
    )
    return localTime in startTime..endTime
}
