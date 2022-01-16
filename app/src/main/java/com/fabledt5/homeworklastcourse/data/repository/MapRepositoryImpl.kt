package com.fabledt5.homeworklastcourse.data.repository

import com.fabledt5.homeworklastcourse.data.db.MarkerEntity
import com.fabledt5.homeworklastcourse.data.db.MarkersDao
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val markersDao: MarkersDao) : MapRepository {

    override suspend fun saveMarker(marker: MarkerEntity) =
        markersDao.insertMarker(marker)

    override suspend fun updateMarker(marker: MarkerEntity) = markersDao.updateMarker(marker)

    override fun getAllMarkers() = markersDao.getAllMarkers()

}


