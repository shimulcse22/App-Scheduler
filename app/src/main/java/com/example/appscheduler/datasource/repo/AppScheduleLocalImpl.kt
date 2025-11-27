package com.example.appscheduler.datasource.repo

import com.example.appscheduler.datasource.database.AppScheduleDao
import com.example.appscheduler.datasource.database.AppScheduleTable
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.datasource.model.Timer
import com.example.appscheduler.utility.NOT_EXECUTED
import com.example.appscheduler.utility.orEmpty
import kotlinx.coroutines.flow.Flow

class AppScheduleLocalImpl(
    private val appDao: AppScheduleDao
) : AppScheduleLocalDs {
    override suspend fun getSchedules(): List<AppScheduleTable> {
        return appDao.getAllSchedules()
    }

    override suspend fun addSchedule(
        installedAppInformation: InstalledAppInformation?,
        timer: Timer?
    ) {
        if (installedAppInformation == null || timer == null) return
        appDao.addSchedule(
            AppScheduleTable(
                appPackage = installedAppInformation.packageNameOfApp.orEmpty(),
                appName = installedAppInformation.appName.orEmpty(),
                date = timer.date,
                hour = timer.hour,
                min = timer.min,
                executionInformation = NOT_EXECUTED
            )
        )
    }

    override suspend fun updateSchedule(schedule: AppScheduleTable?) {
        schedule ?: return
        appDao.updateSchedule(schedule)
    }

    override fun getAppScheduleByPackageName(appPackageName: String): Flow<AppScheduleTable?> {
        return appDao.getAppScheduleByAppPackageName(appPackageName)
    }

}