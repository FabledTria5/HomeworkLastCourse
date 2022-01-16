package com.fabledt5.homeworklastcourse.di

import android.content.Context
import androidx.room.Room
import com.fabledt5.homeworklastcourse.data.db.MarkersDao
import com.fabledt5.homeworklastcourse.data.db.MarkersDataBase
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
    fun provideMarkersDataBase(@ApplicationContext context: Context): MarkersDataBase = Room
        .databaseBuilder(context, MarkersDataBase::class.java, "markers_database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideMarkersDao(markersDataBase: MarkersDataBase): MarkersDao =
        markersDataBase.markersDao()

}