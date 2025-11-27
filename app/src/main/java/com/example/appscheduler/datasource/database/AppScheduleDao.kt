package com.example.appscheduler.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppScheduleDao {
    @Query("SELECT * FROM schedule_table ORDER BY id ASC")
    suspend fun getAllSchedules(): List<AppScheduleTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSchedule(schedule: AppScheduleTable)

    @Update
    suspend fun updateSchedule(schedule: AppScheduleTable)

    @Query("SELECT * FROM schedule_table WHERE appPackage = :appPackageName AND executionInformation = 'Not executed' LIMIT 1")
    fun getAppScheduleByAppPackageName(appPackageName: String): Flow<AppScheduleTable?>
}