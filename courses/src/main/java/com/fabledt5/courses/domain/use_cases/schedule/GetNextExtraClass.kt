package com.fabledt5.courses.domain.use_cases.schedule

import com.fabledt5.courses.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetNextExtraClass @Inject constructor(private val scheduleRepository: ScheduleRepository) {

    operator fun invoke() = scheduleRepository.getNextExtraClass()

}