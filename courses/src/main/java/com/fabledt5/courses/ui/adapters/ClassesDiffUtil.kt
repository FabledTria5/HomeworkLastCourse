package com.fabledt5.courses.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.fabledt5.courses.data.db.entities.ClassEntity

object ClassesDiffUtil : DiffUtil.ItemCallback<ClassEntity>() {

    override fun areItemsTheSame(oldItem: ClassEntity, newItem: ClassEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ClassEntity, newItem: ClassEntity) =
        oldItem == newItem

}