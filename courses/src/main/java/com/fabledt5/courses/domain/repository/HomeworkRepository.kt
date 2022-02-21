package com.fabledt5.courses.domain.repository

import com.fabledt5.courses.domain.model.HomeworkItem
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {

    fun getHomeWorks(): Flow<List<HomeworkItem>>

}