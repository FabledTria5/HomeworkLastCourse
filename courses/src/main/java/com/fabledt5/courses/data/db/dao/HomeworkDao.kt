package com.fabledt5.courses.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.fabledt5.courses.data.db.entities.HomeworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {

    @Query(value = "SELECT * FROM homework_table")
    fun getAllHomework(): Flow<List<HomeworkEntity>>

}