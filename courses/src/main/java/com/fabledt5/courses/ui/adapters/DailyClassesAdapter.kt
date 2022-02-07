package com.fabledt5.courses.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.courses.R
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.databinding.ItemClassBinding
import java.text.SimpleDateFormat
import java.util.*

class DailyClassesAdapter(
    private val onOpenClassClick: () -> Unit,
    private val onScrollToActive: (Int) -> Unit
) : ListAdapter<ClassEntity, DailyClassesAdapter.DailyClassesViewHolder>(ClassesDiffUtil) {

    inner class DailyClassesViewHolder(private val binding: ItemClassBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = getItem(position)

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
                    onScrollToActive(position)
                    binding.openClass.setOnClickListener { onOpenClassClick() }
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