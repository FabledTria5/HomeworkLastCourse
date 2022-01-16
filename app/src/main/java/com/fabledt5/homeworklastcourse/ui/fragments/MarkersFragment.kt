package com.fabledt5.homeworklastcourse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fabledt5.homeworklastcourse.R
import com.fabledt5.homeworklastcourse.data.db.MarkerEntity
import com.fabledt5.homeworklastcourse.databinding.FragmentMarkersBinding
import com.fabledt5.homeworklastcourse.ui.adapters.MarkersListAdapter
import com.fabledt5.homeworklastcourse.ui.viewmodel.MapsViewModel
import com.fabledt5.homeworklastcourse.util.launchWhenStarted
import kotlinx.coroutines.flow.onEach

class MarkersFragment : Fragment(R.layout.fragment_markers) {

    private val mapsViewModel: MapsViewModel by activityViewModels()

    private var _binding: FragmentMarkersBinding? = null
    private val binding get() = _binding!!

    private val onMarkerNameChange: (String, MarkerEntity) -> Unit = { newTitle, marker ->
        mapsViewModel.updateMarkerTitle(newTitle, marker)
    }

    private val onMarkerTextChange: (String, MarkerEntity) -> Unit = { newText, marker ->
        mapsViewModel.updateMarkerText(newText, marker)
    }

    private val markersListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MarkersListAdapter(
            onMarkerNameChange = onMarkerNameChange,
            onMarkerTextChange = onMarkerTextChange
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.markersList.adapter = markersListAdapter

        mapsViewModel.savedMarkers.onEach { markers ->
            markersListAdapter.submitList(markers)
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}