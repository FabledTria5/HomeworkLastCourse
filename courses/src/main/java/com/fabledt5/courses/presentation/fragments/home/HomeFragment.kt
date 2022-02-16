package com.fabledt5.courses.presentation.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import com.fabledt5.courses.R
import com.fabledt5.courses.databinding.FragmentHomeBinding
import com.fabledt5.courses.presentation.adapters.DailyClassesAdapter
import com.fabledt5.courses.presentation.adapters.HomeworkListAdapter
import com.fabledt5.courses.domain.model.Resource
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

        AlphaAnimation(.0f, 1f).apply {
            duration = 500
            startOffset = 50
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }.also { animation ->
            binding.tvDots1.startAnimation(animation)
            binding.tvDots2.startAnimation(animation)
        }
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
                is Resource.Error -> {
                    Log.d(TAG, "observeTimer: ${resource.error.message}")
                    binding.tvDailyClassesCount.visibility = View.INVISIBLE
                    binding.tvNoClasses.visibility = View.VISIBLE

                    if (dailyClassesAdapter.itemCount > 0)
                        dailyClassesAdapter.submitList(emptyList())
                }
                Resource.Loading -> {}
                is Resource.Success -> {
                    dailyClassesAdapter.submitList(resource.data)
                    binding.tvNoClasses.visibility = View.INVISIBLE
                    binding.tvDailyClassesCount.text =
                        String.format(getString(R.string.classes_today), resource.data.size)
                }
            }
        }.launchWhenStarted(lifecycleScope)

        homeViewModel.homework.onEach { resource ->
            when (resource) {
                is Resource.Error -> Log.d(TAG, "observeTimer: ${resource.error.message}")
                Resource.Loading -> {}
                is Resource.Success -> homeworkListAdapter.submitList(resource.data)
            }
        }.launchWhenStarted(lifecycleScope)

        homeViewModel.timeToExam.onEach { timerCount ->
            binding.daysFirstNum.text = timerCount.days.first().toString()
            binding.daysSecondNum.text = timerCount.days.last().toString()

            binding.hoursFirstNum.text = timerCount.hours.first().toString()
            binding.hoursSecondNum.text = timerCount.hours.last().toString()

            binding.minutesFirstNum.text = timerCount.minutes.first().toString()
            binding.minutesSecondNum.text = timerCount.minutes.last().toString()
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