package com.fabledt5.courses.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.courses.R
import com.fabledt5.courses.databinding.ItemHomeworkBinding
import com.fabledt5.courses.domain.model.HomeworkItem

class HomeworkListAdapter :
    ListAdapter<HomeworkItem, HomeworkListAdapter.HomeworkListViewHolder>(HomeworkDiffUtil) {

    private object HomeworkDiffUtil : DiffUtil.ItemCallback<HomeworkItem>() {
        override fun areItemsTheSame(oldItem: HomeworkItem, newItem: HomeworkItem) =
            oldItem.className == newItem.className && oldItem.deadline == newItem.deadline

        override fun areContentsTheSame(oldItem: HomeworkItem, newItem: HomeworkItem) =
            oldItem == newItem
    }

    inner class HomeworkListViewHolder(private val binding: ItemHomeworkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)
            val context = binding.root.context

            with(binding) {
                tvClassName.text = item.className
                tvHomeworkText.text = item.homeworkText
                tvDeadline.text = String.format(context.getString(R.string.days_left), item.deadline)

                if (item.deadline <= 2) tvDeadline.setTextColor(Color.RED)
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