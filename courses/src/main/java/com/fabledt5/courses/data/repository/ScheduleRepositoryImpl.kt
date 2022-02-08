package com.fabledt5.courses.data.repository

import com.fabledt5.courses.data.db.dao.CoursesDao
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.data.db.toDomain
import com.fabledt5.courses.data.remote.RemoteDataSource
import com.fabledt5.courses.data.remote.toEntity
import com.fabledt5.courses.domain.model.ClassItem
import com.fabledt5.courses.domain.repository.ScheduleRepository
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@BoundTo(supertype = ScheduleRepository::class, component = SingletonComponent::class)
class ScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val coursesDao: CoursesDao
) : ScheduleRepository {

    override suspend fun isDataLoaded() = coursesDao.isCoursesDataLoaded() > 0

    override fun getNextExtraClass() = coursesDao.getFirstExtraClass().toDomain()

    override fun getDailyClasses(): Flow<List<ClassItem>> {
        val today = SimpleDateFormat("dd MMM yyyy", Locale("ru"))
            .format(Date())
            .filter { it != '.' }
        return coursesDao.getDailyClasses(today).toDomain()
    }

    override suspend fun loadData() {
        coursesDao.insertClasses(remoteDataSource.getSchedule().toEntity())
    }

}