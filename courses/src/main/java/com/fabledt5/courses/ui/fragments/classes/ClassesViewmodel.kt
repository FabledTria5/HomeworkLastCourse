package com.fabledt5.courses.ui.fragments.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.data.repository.ScheduleRepository
import com.fabledt5.courses.ui.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ClassesViewmodel @Inject constructor(
    scheduleRepository: ScheduleRepository
) :
    ViewModel() {

    private val _dailyClasses = MutableStateFlow<Resource<List<ClassEntity>>>(Resource.Loading)
    val dailyClasses = _dailyClasses.asStateFlow()

    init {
        scheduleRepository.getDailyClasses().onEach {
            _dailyClasses.value = if (it.isNotEmpty()) Resource.Success(data = it)
            else Resource.Error(Throwable("List is empty"))
        }.launchIn(viewModelScope)
    }

}