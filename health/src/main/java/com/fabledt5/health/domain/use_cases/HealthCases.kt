package com.fabledt5.health.domain.use_cases

import javax.inject.Inject

data class HealthCases @Inject constructor(
    val addHealthData: AddHealthData,
    val getHealthData: GetHealthData
)