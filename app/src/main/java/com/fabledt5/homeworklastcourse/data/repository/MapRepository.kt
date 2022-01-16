package com.fabledt5.homeworklastcourse.data.repository

import com.fabledt5.homeworklastcourse.data.db.MarkerEntity
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun saveMarker(marker: MarkerEntity)
    suspend fun updateMarker(marker: MarkerEntity)

    fun getAllMarkers(): Flow<List<MarkerEntity>>
}