package com.fabledt5.courses.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fabledt5.courses.data.db.dao.CoursesDao
import com.fabledt5.courses.data.db.dao.HomeworkDao
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.data.db.entities.HomeworkEntity

@Database(entities = [ClassEntity::class, HomeworkEntity::class], version = 8, exportSchema = false)
abstract class CoursesDataBase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
    abstract fun homeworkDao(): HomeworkDao
}