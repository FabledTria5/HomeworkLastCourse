package com.fabledt5.courses.data.remote

import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.data.remote.dto.ClassResponseItem

var shortMonths = arrayOf(
    "янв", "февр", "мар", "апр", "мая", "июн",
    "июл", "авг", "сент", "окт", "нояб", "дек"
)

fun List<ClassResponseItem>.toEntity() = map { classItem ->
    ClassEntity(
        classDate = run {
            val dateArray = classItem.date.split(" ")
            "${dateArray[0]} ${getMonthProperName(dateArray[1])} ${dateArray[2]}"
        },
        className = classItem.name,
        classTime = classItem.time,
        classType = classItem.type,
        teacherName = classItem.teacherName,
        auditory = classItem.auditory
    )
}

fun getMonthProperName(monthName: String): String {
    for (monthShortName in shortMonths) {
        if (monthShortName.contains(monthName.lowercase())) return monthShortName
    }
    return shortMonths[0]
}

