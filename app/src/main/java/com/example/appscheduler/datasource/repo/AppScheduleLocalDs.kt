package com.example.appscheduler.datasource.repo

import com.example.appscheduler.datasource.database.AppScheduleTable
import kotlinx.coroutines.flow.Flow

interface AppScheduleLocalDs {
    fun getSchedules() : Flow<List<AppScheduleTable>>

    suspend fun addSchedule(schedule: AppScheduleTable)

    suspend fun updateSchedule(schedule: AppScheduleTable)
}