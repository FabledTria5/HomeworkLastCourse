package com.fabledt5.health.domain.use_cases

import com.fabledt5.health.domain.repository.HealthRepository
import javax.inject.Inject

class GetHealthData @Inject constructor(private val healthRepository: HealthRepository) {

    operator fun invoke() = healthRepository.subscribeToData()

}