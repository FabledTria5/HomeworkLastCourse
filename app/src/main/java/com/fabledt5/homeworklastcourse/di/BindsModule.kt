package com.fabledt5.homeworklastcourse.di

import com.fabledt5.homeworklastcourse.data.repository.MapRepository
import com.fabledt5.homeworklastcourse.data.repository.MapRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {

    @Binds
    fun bindMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository

}