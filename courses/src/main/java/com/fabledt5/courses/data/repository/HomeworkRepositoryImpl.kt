package com.fabledt5.courses.data.repository

import com.fabledt5.courses.domain.model.HomeworkItem
import com.fabledt5.courses.domain.repository.HomeworkRepository
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@BoundTo(supertype = HomeworkRepository::class, component = SingletonComponent::class)
class HomeworkRepositoryImpl @Inject constructor() : HomeworkRepository {

    private val homeworkList = listOf(
        HomeworkItem(
            className = "Основы построения защищенных баз данных",
            deadline = 9,
            homeworkText = "Законспектировать первые две лекции и выписать основные определения"
        ),
        HomeworkItem(
            className = "Комплексные системы обеспечения безопасности бизнеса на предприятиях",
            deadline = 10,
            homeworkText = "Подготовиться к проверочной работе по первой лекции"
        ),
        HomeworkItem(
            className = "Методы и средства определения характеристик ЭМ излучения",
            deadline = 10,
            homeworkText = "Законспектировать таблицу с показателями промышленного излучения из учебника"
        ),
        HomeworkItem(
            className = "Техническая защита информации",
            deadline = 12,
            homeworkText = "Изучить материал по эксплуатации лабораторных генераторов высоких частот"
        ),
    )

    override fun getHomeWorks() = flowOf(homeworkList)


}