package com.fabledt5.courses.domain.use_cases.schedule

import com.fabledt5.courses.domain.repository.ScheduleRepository
import javax.inject.Inject

class PrepareData @Inject constructor(private val scheduleRepository: ScheduleRepository) {

    suspend operator fun invoke(): Boolean {
        if (!scheduleRepository.isDataLoaded()) scheduleRepository.loadData()
        return true
    }

}