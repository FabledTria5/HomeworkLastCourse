package com.fabledt5.courses.domain.use_cases.homework

import com.fabledt5.courses.domain.repository.HomeworkRepository
import javax.inject.Inject

class GetHomework @Inject constructor(private val homeworkRepository: HomeworkRepository) {

    operator fun invoke() = homeworkRepository.getHomeWorks()

}