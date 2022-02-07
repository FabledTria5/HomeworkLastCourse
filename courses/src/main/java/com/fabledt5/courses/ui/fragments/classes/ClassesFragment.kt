package com.fabledt5.courses.ui.fragments.classes

import android.content.ActivityNotFoundException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.fabledt5.courses.R
import com.fabledt5.courses.databinding.FragmentClassesBinding
import com.fabledt5.courses.ui.adapters.ClassesExtendedListAdapter
import com.fabledt5.courses.ui.model.Resource
import com.fabledt5.courses.util.launchWhenStarted
import com.lriccardo.timelineview.TimelineDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ClassesFragment : Fragment(R.layout.fragment_classes) {

    private var _binding: FragmentClassesBinding? = null
    private val binding get() = _binding!!

    private val classesViewmodel: ClassesViewmodel by activityViewModels()

    private val onOpenClassClick: () -> Unit = {
        try {
            val packageManager = requireActivity().packageManager
            val skype = packageManager.getLaunchIntentForPackage("com.skype.raider")
            startActivity(skype)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Skype is not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private val extendedClassesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ClassesExtendedListAdapter(onOpenClassClick = onOpenClassClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        observeData()
    }

    private fun initUi() {
        binding.rvClasses.adapter = extendedClassesAdapter
        binding.rvClasses.addItemDecoration(
            TimelineDecorator(
                indicatorSize = 15f,
                lineWidth = 1f,
                position = TimelineDecorator.Position.Left,
                indicatorColor = ContextCompat.getColor(requireContext(), R.color.SpringGreen),
                lineColor = ContextCompat.getColor(requireContext(), R.color.SpringGreen),
                padding = 0f,
                indicatorYPosition = .05f
            )
        )
    }

    private fun observeData() {
        classesViewmodel.dailyClasses.onEach { resource ->
            when (resource) {
                is Resource.Error -> {}
                Resource.Loading -> {}
                is Resource.Success -> {
                    extendedClassesAdapter.submitList(resource.data)
                    binding.tvToday.text = String.format(
                        getString(R.string.today_date),
                        SimpleDateFormat("d MMMM", Locale.getDefault()).format(Date())
                    )
                }
            }
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}