package com.example.appscheduler.listeners

import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.datasource.model.Timer

interface OnIItemClickListener {
    fun onItemClick(installedAppInformation: InstalledAppInformation?)

    fun setSchedule(time : Timer)
}