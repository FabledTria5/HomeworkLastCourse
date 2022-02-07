package com.fabledt5.courses.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.courses.R
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.databinding.ItemClassBinding
import com.fabledt5.courses.databinding.ItemClassExtendedBinding
import java.text.SimpleDateFormat
import java.util.*

class ClassesExtendedListAdapter(private val onOpenClassClick: () -> Unit) :
    ListAdapter<ClassEntity, ClassesExtendedListAdapter.ClassesExtendedListViewHolder>(
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

                val localTime = Date()

                val startTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                    .parse(item.classTime.substring(0, 5))

                val endTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                    .parse(
                        item.classTime.substring(
                            item.classTime.length - 5, item.classTime.length
                        )
                    )

                if (localTime in startTime..endTime) {
                    binding.openClass.visibility = View.VISIBLE
                    binding.openClass.setOnClickListener { onOpenClassClick() }
                }

                if (!item.classType.lowercase().contains("лекция"))
                    binding.parentLayout.background = GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        intArrayOf(
                            ContextCompat.getColor(context, R.color.SpringGreen),
                            ContextCompat.getColor(context, R.color.DarkTurquoise)
                        )
                    )
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