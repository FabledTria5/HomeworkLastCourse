package com.fabledt5.courses.domain.use_cases.schedule

import com.fabledt5.courses.domain.repository.ScheduleRepository
import com.fabledt5.courses.domain.model.Resource
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetNextExtraClass @Inject constructor(private val scheduleRepository: ScheduleRepository) {

    operator fun invoke() = scheduleRepository.getNextExtraClass("лекция")
        .map { list ->
            if (list.isNotEmpty())
                Resource.Success(data = list.first { it.classDate.time > Date().time })
            else
                Resource.Error(error = Throwable("No extra classes"))
        }

}