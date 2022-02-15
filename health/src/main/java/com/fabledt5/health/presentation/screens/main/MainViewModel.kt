package com.fabledt5.health.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.health.domain.model.HealthItem
import com.fabledt5.health.domain.model.Resource
import com.fabledt5.health.domain.use_cases.HealthCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val healthCases: HealthCases) : ViewModel() {

    private val _healthItems = MutableStateFlow<Resource<List<HealthItem>>>(Resource.Loading)
    val healthItems = _healthItems.asStateFlow()

    init {
        readHealthData()
    }

    private fun readHealthData() = healthCases.getHealthData().onEach { resource ->
        _healthItems.value = resource
    }.launchIn(viewModelScope)

    fun saveHealthData(lowPressure: String, highPressure: String, pulse: String) =
        healthCases.addHealthData(lowPressure, highPressure, pulse)

    fun deleteHealthData(dateAdded: String, timeAdded: String) =
        healthCases.deleteHealthData(dateAdded, timeAdded)

}