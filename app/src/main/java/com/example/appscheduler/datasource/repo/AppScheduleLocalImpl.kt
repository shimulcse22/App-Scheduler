package com.example.appscheduler.datasource.repo

import com.example.appscheduler.datasource.database.AppScheduleDao
import com.example.appscheduler.datasource.database.AppScheduleTable
import kotlinx.coroutines.flow.Flow

class AppScheduleLocalImpl(
    private val appDao: AppScheduleDao
) : AppScheduleLocalDs {
    override fun getSchedules(): Flow<List<AppScheduleTable>> {
        return appDao.getAllSchedules()
    }

    override suspend fun addSchedule(schedule: AppScheduleTable) {
        appDao.addSchedule(schedule)
    }

    override suspend fun updateSchedule(schedule: AppScheduleTable) {
        appDao.updateSchedule(schedule)
    }

}