package com.fabledt5.courses.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.courses.R
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.databinding.ItemClassBinding

class DailyClassesAdapter :
    ListAdapter<ClassEntity, DailyClassesAdapter.DailyClassesViewHolder>(ClassesDiffUtil) {

    private object ClassesDiffUtil : DiffUtil.ItemCallback<ClassEntity>() {

        override fun areItemsTheSame(oldItem: ClassEntity, newItem: ClassEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ClassEntity, newItem: ClassEntity) =
            oldItem == newItem

    }

    inner class DailyClassesViewHolder(private val binding: ItemClassBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ClassEntity) {
            with(binding) {
                tvClassName.text = item.className
                tvTeacherName.text = item.teacherName
                tvClassTime.text = item.classTime
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
        holder.bind(getItem(position))

}