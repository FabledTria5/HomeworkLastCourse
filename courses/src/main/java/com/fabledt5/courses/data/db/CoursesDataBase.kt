package com.fabledt5.courses.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fabledt5.courses.data.db.dao.CoursesDao
import com.fabledt5.courses.data.db.entities.ClassEntity

@Database(entities = [ClassEntity::class], version = 9, exportSchema = false)
abstract class CoursesDataBase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
}