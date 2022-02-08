package com.fabledt5.courses.domain.use_cases.schedule

import javax.inject.Inject

data class ScheduleCases @Inject constructor(
    val prepareData: PrepareData,
    val getNextExtraClass: GetNextExtraClass,
    val getDailyClasses: GetDailyClasses
)
