package com.fabledt5.courses.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.fabledt5.courses.R
import com.fabledt5.courses.databinding.FragmentHomeBinding
import com.fabledt5.courses.util.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        observeTimer()
    }

    private fun initUi() {

    }

    private fun observeTimer() {
        homeViewModel.daysToExam.onEach { daysNumber ->
            val daysString = daysNumber.toString()
            if (daysString.length == 2) {
                binding.daysFirstNum.text = daysString[0].toString()
                binding.daysSecondNum.text = daysString[1].toString()
            } else {
                binding.daysSecondNum.text = daysString[0].toString()
            }
        }.launchWhenStarted(lifecycleScope)

        homeViewModel.hoursToExam.onEach { hoursNumber ->
            val hoursString = hoursNumber.toString()
            if (hoursString.length == 2) {
                binding.hoursFirstNum.text = hoursString[0].toString()
                binding.hoursSecondNum.text = hoursString[1].toString()
            } else {
                binding.hoursSecondNum.text = hoursString[0].toString()
            }
        }.launchWhenStarted(lifecycleScope)

        homeViewModel.minutesToExam.onEach { minutesNumber ->
            val minutesString = minutesNumber.toString()
            if (minutesString.length == 2) {
                binding.minutesFirstNum.text = minutesString[0].toString()
                binding.minutesSecondNum.text = minutesString[1].toString()
            } else {
                binding.minutesSecondNum.text = minutesString[0].toString()
            }
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}