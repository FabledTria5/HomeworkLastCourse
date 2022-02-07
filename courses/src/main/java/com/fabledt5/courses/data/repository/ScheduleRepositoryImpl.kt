package com.fabledt5.courses.data.repository

import com.fabledt5.courses.data.db.CoursesDao
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.data.remote.RemoteDataSource
import com.fabledt5.courses.data.remote.toEntity
import com.fabledt5.courses.ui.model.Resource
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@BoundTo(supertype = ScheduleRepository::class, component = SingletonComponent::class)
class ScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val coursesDao: CoursesDao
) : ScheduleRepository {

    override suspend fun isDataLoaded() = coursesDao.isCoursesDataLoaded() > 0

    override fun getNextExtraClass() = coursesDao.getFirstExtraClass()

    override fun getDailyClasses(date: String) = coursesDao.getDailyClasses(date = date)

    override suspend fun loadData() {
        coursesDao.insertClasses(remoteDataSource.getSchedule().toEntity())
    }

}