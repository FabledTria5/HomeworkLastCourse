package com.fabledt5.courses.ui.fragments.home

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.data.db.entities.HomeworkEntity
import com.fabledt5.courses.data.repository.HomeworkRepository
import com.fabledt5.courses.data.repository.ScheduleRepository
import com.fabledt5.courses.ui.model.Resource
import com.fabledt5.courses.util.toDaysDifference
import com.fabledt5.courses.util.toHoursDifference
import com.fabledt5.courses.util.toMinutesDifference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val homeworkRepository: HomeworkRepository
) : ViewModel() {

    private val _daysToExam = MutableStateFlow<Long>(value = 0)
    val daysToExam = _daysToExam.asStateFlow()

    private val _hoursToExam = MutableStateFlow(value = 0)
    val hoursToExam = _hoursToExam.asStateFlow()

    private val _minutesToExam = MutableStateFlow(value = 0)
    val minutesToExam = _minutesToExam.asStateFlow()

    private val _examName = MutableStateFlow(value = "")
    val examName = _examName.asStateFlow()

    private val _allClasses = MutableStateFlow<Resource<List<ClassEntity>>>(Resource.Loading)
    val allClasses = _allClasses.asStateFlow()

    private val _homework = MutableStateFlow<Resource<List<HomeworkEntity>>>(Resource.Loading)
    val homework = _homework.asStateFlow()

    val isDataLoading = MutableStateFlow(value = true)

    private var timer: CountDownTimer? = null

    init {
        updateData()
    }

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            isDataLoading.value = true
            if (!scheduleRepository.isDataLoaded()) scheduleRepository.loadData()
            viewModelScope.launch(Dispatchers.Main) {
                getExtraClass()
                getDailyClasses()
                getHomework()
                isDataLoading.value = false
            }
        }
    }

    private fun getExtraClass() {
        scheduleRepository.getNextExtraClass().onEach { classEntity ->
            val classDateString = classEntity.classDate + " " + classEntity.classTime
            val classDate = SimpleDateFormat("dd MMM yyyy HH:mm - HH:mm", Locale("ru"))
                .parse(classDateString) ?: Date()
            val today = Date()

            val timeDifference = classDate.time - today.time

            _examName.value = classEntity.className
            viewModelScope.launch(Dispatchers.Main) {
                startTimer(
                    daysToExam = timeDifference.toDaysDifference(),
                    timeDifference.toHoursDifference(),
                    timeDifference.toMinutesDifference()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getDailyClasses() {
        scheduleRepository.getDailyClasses().onEach {
            _allClasses.value = if (it.isNotEmpty()) Resource.Success(data = it)
            else Resource.Error(error = Throwable("Empty list"))
        }.launchIn(viewModelScope)
    }

    private fun getHomework() {
        homeworkRepository.getHomeWorks().onEach {
            _homework.value = if (it.isNotEmpty()) Resource.Success(data = it)
            else Resource.Error(error = Throwable("Empty list"))
        }.launchIn(viewModelScope)
    }

    private fun startTimer(daysToExam: Long, hoursToExam: Long, minutesToExam: Long) {
        timer?.cancel()

        val interval = 1.minutes.inWholeMilliseconds
        val time = (daysToExam.days + hoursToExam.hours + minutesToExam.minutes).inWholeMilliseconds

        timer = object : CountDownTimer(time, interval) {
            override fun onTick(millisUntilFinished: Long) {
                Duration.parse(millisUntilFinished.milliseconds.toIsoString())
                    .toComponents { days, hours, minutes, _, _ ->
                        _daysToExam.value = days
                        _hoursToExam.value = hours
                        _minutesToExam.value = minutes
                    }
            }

            override fun onFinish() = Unit
        }
        timer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        timer = null
    }
}