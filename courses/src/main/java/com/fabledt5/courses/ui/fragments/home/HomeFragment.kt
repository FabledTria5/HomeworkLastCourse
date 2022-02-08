package com.fabledt5.courses.ui.fragments.home

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import com.fabledt5.courses.R
import com.fabledt5.courses.databinding.FragmentHomeBinding
import com.fabledt5.courses.ui.adapters.DailyClassesAdapter
import com.fabledt5.courses.ui.adapters.HomeworkListAdapter
import com.fabledt5.courses.ui.model.Resource
import com.fabledt5.courses.util.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val onOpenClassClick: () -> Unit = {
        try {
            val packageManager = requireActivity().packageManager
            val skype = packageManager.getLaunchIntentForPackage("com.skype.raider")
            startActivity(skype)
        } catch (e: NullPointerException) {
            Toast.makeText(context, "Skype is not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private val dailyClassesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DailyClassesAdapter(
            onOpenClassClick = onOpenClassClick,
            onScrollToActive = { binding.rvClasses.scrollToPosition(it) })
    }

    private val homeworkListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HomeworkListAdapter()
    }

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
        setupListeners()
        observeData()
    }

    private fun initUi() {
        LinearSnapHelper().attachToRecyclerView(binding.rvClasses)
        binding.rvClasses.adapter = dailyClassesAdapter
        binding.rvHomework.adapter = homeworkListAdapter
    }

    private fun setupListeners() {
        binding.refreshLayout.setOnRefreshListener { homeViewModel.updateData() }
    }

    private fun observeData() {
        homeViewModel.isDataLoading.onEach {
            binding.refreshLayout.isRefreshing = it
        }.launchWhenStarted(lifecycleScope)

        homeViewModel.allClasses.onEach { resource ->
            when (resource) {
                is Resource.Error -> Log.d(TAG, "observeTimer: ${resource.error.message}")
                Resource.Loading -> {}
                is Resource.Success -> {
                    dailyClassesAdapter.submitList(resource.data)
                    binding.tvDailyClassesCount.text =
                        String.format(getString(R.string.classes_today), resource.data.size)
                }
            }
        }.launchWhenStarted(lifecycleScope)

        homeViewModel.homework.onEach { resource ->
            when(resource) {
                is Resource.Error -> Log.d(TAG, "observeTimer: ${resource.error.message}")
                Resource.Loading -> {}
                is Resource.Success -> homeworkListAdapter.submitList(resource.data)
            }
        }.launchWhenStarted(lifecycleScope)

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

        homeViewModel.examName.onEach { name ->
            binding.tvExamName.text = name
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}