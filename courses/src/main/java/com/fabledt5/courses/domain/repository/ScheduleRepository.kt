package com.fabledt5.courses.domain.repository

import com.fabledt5.courses.domain.model.ClassItem
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    suspend fun isDataLoaded(): Boolean

    fun getNextExtraClass(excludeClasses: String): Flow<List<ClassItem>>

    fun getDailyClasses(): Flow<List<ClassItem>>

    suspend fun loadData()

}