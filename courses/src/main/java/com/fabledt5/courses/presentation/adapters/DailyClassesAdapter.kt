package com.fabledt5.courses.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.courses.R
import com.fabledt5.courses.databinding.ItemClassBinding
import com.fabledt5.courses.domain.model.ClassItem

class DailyClassesAdapter(
    private val onOpenClassClick: () -> Unit,
    private val onScrollToActive: (Int) -> Unit
) : ListAdapter<ClassItem, DailyClassesAdapter.DailyClassesViewHolder>(ClassesDiffUtil) {

    inner class DailyClassesViewHolder(private val binding: ItemClassBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = getItem(position)

            with(binding) {
                tvClassName.text = item.className
                tvTeacherName.text = item.teacherName
                tvClassTime.text = item.classTime

                if (item.isRunning) {
                    binding.openClass.visibility = View.VISIBLE
                    onScrollToActive(position)
                    binding.btnOpenLesson.setOnClickListener { onOpenClassClick() }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DailyClassesViewHolder(
        ItemClassBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_class,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: DailyClassesViewHolder, position: Int) =
        holder.bind(position)

}