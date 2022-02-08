package com.fabledt5.courses.domain.repository

import com.fabledt5.courses.domain.model.ClassItem
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    suspend fun isDataLoaded(): Boolean

    fun getNextExtraClass(): Flow<ClassItem>

    fun getDailyClasses(): Flow<List<ClassItem>>

    suspend fun loadData()

}