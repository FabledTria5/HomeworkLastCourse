package com.fabledt5.courses.di

import android.content.Context
import androidx.room.Room
import com.fabledt5.courses.data.db.CoursesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideCoursesDatabase(@ApplicationContext context: Context): CoursesDataBase = Room
        .databaseBuilder(context, CoursesDataBase::class.java, "courses_database")
        .createFromAsset("database/homeworks.db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideCoursesDao(coursesDataBase: CoursesDataBase) = coursesDataBase.coursesDao()

    @Singleton
    @Provides
    fun provideHomeworkDao(coursesDataBase: CoursesDataBase) = coursesDataBase.homeworkDao()

}