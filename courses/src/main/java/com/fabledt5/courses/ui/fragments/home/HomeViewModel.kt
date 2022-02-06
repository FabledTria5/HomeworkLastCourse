package com.fabledt5.courses.ui.fragments.home

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.courses.data.repository.ScheduleRepository
import com.fabledt5.courses.util.toDaysDifference
import com.fabledt5.courses.util.toHoursDifference
import com.fabledt5.courses.util.toMinutesDifference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class HomeViewModel @Inject constructor(private val scheduleRepository: ScheduleRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _daysToExam = MutableStateFlow<Long>(0)
    val daysToExam = _daysToExam.asStateFlow()

    private val _hoursToExam = MutableStateFlow(0)
    val hoursToExam = _hoursToExam.asStateFlow()

    private val _minutesToExam = MutableStateFlow(0)
    val minutesToExam = _minutesToExam.asStateFlow()

    private var timer: CountDownTimer? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (!scheduleRepository.isDataLoaded()) scheduleRepository.loadData()
            getExtraClass()

        }
    }

    private suspend fun getExtraClass() {
        scheduleRepository.getNextExtraClass().collect { classEntity ->
            val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm - HH:mm", Locale("ru"))
            val classDateString = classEntity.classDate + " " + classEntity.classTime
            val classDate = simpleDateFormat.parse(classDateString) ?: Date()
            val today = Date()

            val timeDifference = classDate.time - today.time

            viewModelScope.launch(Dispatchers.Main) {
                startTimer(
                    daysToExam = timeDifference.toDaysDifference(),
                    timeDifference.toHoursDifference(),
                    timeDifference.toMinutesDifference()
                )
            }
        }
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