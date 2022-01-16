package com.fabledt5.homeworklastcourse.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MarkerEntity::class], version = 3, exportSchema = false)
abstract class MarkersDataBase : RoomDatabase() {
    abstract fun markersDao(): MarkersDao
}