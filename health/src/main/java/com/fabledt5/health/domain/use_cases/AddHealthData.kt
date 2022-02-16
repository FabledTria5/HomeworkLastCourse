package com.fabledt5.health.domain.use_cases

import com.fabledt5.health.domain.repository.HealthRepository
import javax.inject.Inject

class AddHealthData @Inject constructor(private val healthRepository: HealthRepository) {

    operator fun invoke(lowPressure: String, highPressure: String, pulse: String) =
        healthRepository.saveData(lowPressure, highPressure, pulse)

}