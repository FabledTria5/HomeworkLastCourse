package com.fabledt5.courses.data.repository

import com.fabledt5.courses.data.db.dao.HomeworkDao
import com.fabledt5.courses.domain.repository.HomeworkRepository
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = HomeworkRepository::class, component = SingletonComponent::class)
class HomeworkRepositoryImpl @Inject constructor(private val homeworkDao: HomeworkDao) :
    HomeworkRepository {

    override fun getHomeWorks() = homeworkDao.getAllHomework()

}