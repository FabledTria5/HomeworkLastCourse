package com.fabledt5.health.domain.repository

import com.fabledt5.health.domain.model.HealthItem
import com.fabledt5.health.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface HealthRepository {

    fun subscribeToData(): Flow<Resource<List<HealthItem>>>

    fun saveData(lowPressure: String, highPressure: String, pulse: String)

    fun deleteData(dataAddedTime: String)

}