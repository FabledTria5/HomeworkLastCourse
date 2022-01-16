package com.fabledt5.homeworklastcourse.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(markerEntity: MarkerEntity)

    @Update
    suspend fun updateMarker(markerEntity: MarkerEntity)

    @Query(value = "SELECT * FROM markers_table")
    fun getAllMarkers(): Flow<List<MarkerEntity>>

}