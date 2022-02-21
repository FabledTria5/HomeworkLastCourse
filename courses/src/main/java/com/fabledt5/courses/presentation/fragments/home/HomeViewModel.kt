package com.fabledt5.courses.presentation.fragments.home

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.courses.domain.model.ClassItem
import com.fabledt5.courses.domain.model.HomeworkItem
import com.fabledt5.courses.domain.repository.HomeworkRepository
import com.fabledt5.courses.domain.use_cases.schedule.ScheduleCases
import com.fabledt5.courses.domain.model.Resource
import com.fabledt5.courses.presentation.model.TimerCount
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
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduleCases: ScheduleCases,
    private val homeworkRepository: HomeworkRepository
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _timeToExam = MutableStateFlow(TimerCount())
    val timeToExam = _timeToExam.asStateFlow()

    private val _examName = MutableStateFlow(value = "")
    val examName = _examName.asStateFlow()

    private val _allClasses = MutableStateFlow<Resource<List<ClassItem>>>(Resource.Loading)
    val allClasses = _allClasses.asStateFlow()

    private val _homework = MutableStateFlow<Resource<List<HomeworkItem>>>(Resource.Loading)
    val homework = _homework.asStateFlow()

    val isDataLoading = MutableStateFlow(value = true)

    private var timer: CountDownTimer? = null

    init { updateData() }

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            isDataLoading.value = true
            scheduleCases.prepareData()
            viewModelScope.launch(Dispatchers.Main) {
                getExtraClass()
                getDailyClasses()
                getHomework()
                isDataLoading.value = false
            }
        }
    }

    private fun getExtraClass() {
        scheduleCases.getNextExtraClass().onEach { result ->
            when(result) {
                is Resource.Error -> Log.e(TAG, "getExtraClass: ", result.error)
                Resource.Loading -> Unit
                is Resource.Success -> {
                    val classItem = result.data
                    val timeDifference = classItem.classDate.time - Date().time

                    _examName.value = classItem.className
                    viewModelScope.launch(Dispatchers.Main) {
                        startTimer(
                            daysToExam = timeDifference.toDaysDifference(),
                            hoursToExam = timeDifference.toHoursDifference(),
                            minutesToExam = timeDifference.toMinutesDifference()
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getDailyClasses() {
        scheduleCases.getDailyClasses().onEach {
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
                        val daysString = if ("$days".length == 1) "0$days" else "$days"
                        val hoursString = if ("$hours".length == 1) "0$hours" else "$hours"
                        val minutesString = if ("$minutes".length == 1) "0$minutes" else "$minutes"

                        _timeToExam.value = TimerCount(daysString, hoursString, minutesString)
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