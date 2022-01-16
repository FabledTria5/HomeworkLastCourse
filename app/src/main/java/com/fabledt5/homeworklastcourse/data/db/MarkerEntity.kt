package com.fabledt5.homeworklastcourse.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "markers_table")
data class MarkerEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "marker_name")
    val markerName: String = "",
    @ColumnInfo(name = "marker_annotation")
    val markerAnnotation: String = ""
)
