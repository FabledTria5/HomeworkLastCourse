package com.fabledt5.courses.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fabledt5.courses.data.db.entities.ClassEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoursesDao {

    @Insert
    suspend fun insertClasses(classes: List<ClassEntity>)

    @Query(value = "SELECT COUNT(*) FROM classes_table")
    suspend fun isCoursesDataLoaded(): Int

    @Query(value = "SELECT * FROM classes_table WHERE class_type NOT LIKE 'лекция' ORDER BY id LIMIT 1")
    fun getFirstExtraClass(): Flow<ClassEntity>

    @Query(value = "SELECT * FROM classes_table")
    fun getAllClasses(): Flow<List<ClassEntity>>

    @Query(value = "SELECT * FROM classes_table WHERE class_date = :date")
    fun getDailyClasses(date: String): Flow<List<ClassEntity>>

}