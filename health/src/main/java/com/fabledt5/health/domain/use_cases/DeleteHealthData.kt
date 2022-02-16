package com.fabledt5.health.domain.use_cases

import com.fabledt5.health.domain.repository.HealthRepository
import javax.inject.Inject

class DeleteHealthData @Inject constructor(private val healthRepository: HealthRepository) {

    operator fun invoke(dateAdded: String, timeAdded: String) =
        healthRepository.deleteData(dataAddedTime = "$dateAdded $timeAdded")

}