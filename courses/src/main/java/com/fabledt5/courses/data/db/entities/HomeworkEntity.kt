package com.fabledt5.courses.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homework_table")
data class HomeworkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "class_name")
    val className: String,
    @ColumnInfo(name = "deadline")
    val deadline: String,
    @ColumnInfo(name = "homework_text")
    val homeworkText: String
)