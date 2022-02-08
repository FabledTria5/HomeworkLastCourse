package com.fabledt5.courses.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.fabledt5.courses.data.db.entities.ClassEntity
import com.fabledt5.courses.domain.model.ClassItem

object ClassesDiffUtil : DiffUtil.ItemCallback<ClassItem>() {

    override fun areItemsTheSame(oldItem: ClassItem, newItem: ClassItem) =
        oldItem.isRunning == newItem.isRunning

    override fun areContentsTheSame(oldItem: ClassItem, newItem: ClassItem) =
        oldItem == newItem

}