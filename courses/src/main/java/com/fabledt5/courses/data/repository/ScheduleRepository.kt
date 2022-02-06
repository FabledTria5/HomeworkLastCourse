package com.fabledt5.courses.data.repository

import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.ui.model.Resource
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    suspend fun isDataLoaded(): Boolean

    fun getNextExtraClass(): Flow<ClassEntity>

    fun getAllClasses(): Flow<ClassEntity>

    suspend fun loadData()

}