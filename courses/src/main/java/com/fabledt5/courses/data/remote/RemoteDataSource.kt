package com.fabledt5.courses.data.remote

import com.fabledt5.courses.data.remote.dto.ClassResponseItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object RemoteDataSource {

    private const val SCHEDULE_URL =
        "https://sd.mstuca1.ru/d/full?fac=3&flow=1&grp=1&lsubgrp=3&esubgrp=0"
    private const val SCHEDULE_TABLE_NAME = "shadow"

    private val CLASSES_TIME = mapOf(
        1 to "8:30 - 10:00",
        2 to "10:10 - 11:40",
        3 to "12:40 - 14:10",
        4 to "14:20 - 15:50",
        5 to "16:20 - 17:50",
        6 to "18:00 - 19:30"
    )

    fun getSchedule(): List<ClassResponseItem> {
        val document = Jsoup.connect(SCHEDULE_URL).get()
        val table = document.select("table.$SCHEDULE_TABLE_NAME").last()
        requireNotNull(table)

        return extractClasses(table)
    }

    private fun extractClasses(table: Element): List<ClassResponseItem> {
        val classesList = arrayListOf<ClassResponseItem>()
        val tableRows = table.select("tr")

        for (i in 1 until tableRows.size) {
            val row = tableRows[i]
            val date = row.select("td").first()?.text()
            requireNotNull(date)

            val columns = row.select("td")

            columns.forEachIndexed { index, column ->
                if (column.className().lowercase().contains("day")) {
                    // Поля с !! всегда существуют
                    val classItem = ClassResponseItem(
                        date = date,
                        time = CLASSES_TIME[index - 2] ?: "Unknown time",
                        name = column.select("b").text(),
                        teacherName = column.select("small").text(),
                        auditory = column.select("u").first()!!.text(),
                        type = column.select("u").last()!!.text()
                    )
                    classesList.add(classItem)
                }
            }
        }
        return classesList
    }

}