package com.fabledt5.homeworklastcourse.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.homeworklastcourse.R
import com.fabledt5.homeworklastcourse.data.db.MarkerEntity
import com.fabledt5.homeworklastcourse.databinding.MarkerItemBinding
import com.google.android.gms.maps.model.LatLng

class MarkersListAdapter(
    private val onMarkerNameChange: (String, MarkerEntity) -> Unit,
    private val onMarkerTextChange: (String, MarkerEntity) -> Unit
) : ListAdapter<MarkerEntity, MarkersListAdapter.MarkersListViewHolder>(MarkerDiffUtil) {

    private object MarkerDiffUtil : DiffUtil.ItemCallback<MarkerEntity>() {

        override fun areItemsTheSame(oldItem: MarkerEntity, newItem: MarkerEntity) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: MarkerEntity, newItem: MarkerEntity) =
            oldItem.id == newItem.id
                    && oldItem.markerName == newItem.markerName
                    && oldItem.markerAnnotation == newItem.markerAnnotation
    }

    inner class MarkersListViewHolder(private val binding: MarkerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            binding.tvMarkerName.text = String.format(
                binding.root.context.getString(R.string.marker_item_template),
                item.latitude,
                item.longitude
            )

            binding.etMarkerName.setText(item.markerName)
            binding.etMarkerAnnotation.setText(item.markerAnnotation)

            binding.etMarkerName.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE)
                    onMarkerNameChange(binding.etMarkerName.text.toString(), item)
                return@setOnEditorActionListener true
            }

            binding.etMarkerAnnotation.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE)
                    onMarkerTextChange(binding.etMarkerAnnotation.text.toString(), item)
                return@setOnEditorActionListener true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MarkersListViewHolder(
        MarkerItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.marker_item,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: MarkersListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}