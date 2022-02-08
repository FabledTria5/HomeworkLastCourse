package com.fabledt5.courses.domain.repository

import com.fabledt5.courses.data.db.entities.HomeworkEntity
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {

    fun getHomeWorks(): Flow<List<HomeworkEntity>>

}