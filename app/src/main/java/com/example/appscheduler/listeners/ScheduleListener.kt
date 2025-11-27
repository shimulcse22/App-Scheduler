package com.example.appscheduler.listeners

import com.example.appscheduler.datasource.model.Timer

interface ScheduleListener {
    fun onCancelButtonClick(timer: Timer?)
}