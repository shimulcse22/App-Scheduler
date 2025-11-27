package com.example.appscheduler.datasource.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class AppScheduleTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val appPackage: String? = "",
    val appName : String? = "",
    val date: String? = "",
    val hour: Int?,
    val min :Int?,
    val executionInformation : String?="",
)