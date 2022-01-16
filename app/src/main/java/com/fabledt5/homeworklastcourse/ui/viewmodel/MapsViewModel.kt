package com.fabledt5.homeworklastcourse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.homeworklastcourse.data.db.MarkerEntity
import com.fabledt5.homeworklastcourse.data.repository.MapRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val mapRepository: MapRepository) : ViewModel() {

    private val _savedMarkers = MutableStateFlow<List<MarkerEntity>>(listOf())
    val savedMarkers = _savedMarkers.asStateFlow()

    init {
        mapRepository.getAllMarkers().onEach { result ->
            _savedMarkers.value = result
        }.launchIn(viewModelScope)
    }

    fun saveMarker(coordinates: LatLng) = viewModelScope.launch(Dispatchers.IO) {
        mapRepository.saveMarker(
            MarkerEntity(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude
            )
        )
    }

    fun updateMarkerTitle(newName: String, marker: MarkerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            val newMarker = marker.copy(markerName = newName)
            mapRepository.updateMarker(newMarker)
        }

    fun updateMarkerText(newAnnotation: String, marker: MarkerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            val newMarker = marker.copy(markerAnnotation = newAnnotation)
            mapRepository.updateMarker(newMarker)
        }

}