package com.example.appscheduler.datasource.repo

import com.example.appscheduler.datasource.database.AppScheduleTable
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.datasource.model.Timer
import kotlinx.coroutines.flow.Flow

interface AppScheduleLocalDs {
    suspend fun getSchedules() : List<AppScheduleTable>

    suspend fun addSchedule(installedAppInformation: InstalledAppInformation?, timer : Timer?)

    suspend fun updateSchedule(schedule: AppScheduleTable?)

    fun getAppScheduleByPackageName(appPackageName : String) : Flow<AppScheduleTable?>
}