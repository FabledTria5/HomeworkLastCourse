package com.fabledt5.courses.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes_table")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "class_name")
    val className: String,
    @ColumnInfo(name = "class_date")
    val classDate: String,
    @ColumnInfo(name = "class_time")
    val classTime: String,
    @ColumnInfo(name = "teacher_name")
    val teacherName: String,
    @ColumnInfo(name = "auditory")
    val auditory: String,
    @ColumnInfo(name = "class_type")
    val classType: String

)
