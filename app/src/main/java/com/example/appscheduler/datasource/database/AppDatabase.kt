package com.example.appscheduler.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppScheduleTable::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): AppScheduleDao
}