package com.fabledt5.courses.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.courses.R
import com.fabledt5.courses.databinding.ItemClassExtendedBinding
import com.fabledt5.courses.domain.model.ClassItem
import com.fabledt5.courses.domain.model.ClassType
import com.fabledt5.courses.util.setLabWorkGradientBackground
import com.fabledt5.courses.util.setPracticeGradientBackground

class ClassesExtendedListAdapter(private val onOpenClassClick: () -> Unit) :
    ListAdapter<ClassItem, ClassesExtendedListAdapter.ClassesExtendedListViewHolder>(
        ClassesDiffUtil
    ) {

    inner class ClassesExtendedListViewHolder(private val binding: ItemClassExtendedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)
            val context = binding.root.context

            with(binding) {
                tvClassName.text = item.className
                tvTeacherName.text = item.teacherName
                tvClassTime.text = item.classTime

                if (item.isRunning) {
                    binding.openClass.visibility = View.VISIBLE
                    binding.openClass.setOnClickListener { onOpenClassClick() }
                }

                when (item.classType) {
                    ClassType.Practice -> binding.parentLayout.setPracticeGradientBackground()
                    ClassType.LabWork -> binding.parentLayout.setLabWorkGradientBackground()
                    else -> Unit
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ClassesExtendedListViewHolder(
            ItemClassExtendedBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_class_extended,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: ClassesExtendedListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}