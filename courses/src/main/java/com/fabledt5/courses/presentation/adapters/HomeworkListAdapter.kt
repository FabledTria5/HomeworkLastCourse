package com.fabledt5.courses.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.courses.R
import com.fabledt5.courses.data.db.entities.HomeworkEntity
import com.fabledt5.courses.databinding.ItemHomeworkBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HomeworkListAdapter :
    ListAdapter<HomeworkEntity, HomeworkListAdapter.HomeworkListViewHolder>(HomeworkDiffUtil) {

    private object HomeworkDiffUtil : DiffUtil.ItemCallback<HomeworkEntity>() {
        override fun areItemsTheSame(oldItem: HomeworkEntity, newItem: HomeworkEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HomeworkEntity, newItem: HomeworkEntity) =
            oldItem == newItem
    }

    inner class HomeworkListViewHolder(private val binding: ItemHomeworkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            with(binding) {
                tvClassName.text = item.className
                tvHomeworkText.text = item.homeworkText

                val today = Date()
                val deadlineDate =
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(item.deadline)
                        ?: Date()

                val daysToDeadline = TimeUnit.DAYS.convert(
                    (deadlineDate.time - today.time),
                    TimeUnit.MILLISECONDS
                )

                tvDeadline.text = String.format(
                    binding.root.context.getString(R.string.days_left),
                    daysToDeadline
                )

                if (daysToDeadline <= 2) tvDeadline.setTextColor(Color.RED)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeworkListViewHolder(
        ItemHomeworkBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_homework,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: HomeworkListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}