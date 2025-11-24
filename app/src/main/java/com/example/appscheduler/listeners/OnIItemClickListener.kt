package com.example.appscheduler.listeners

import com.example.appscheduler.datasource.model.Timer

interface OnIItemClickListener {
    fun onItemClick(appPackageName : String?)

    fun setSchedule(time : Timer)
}