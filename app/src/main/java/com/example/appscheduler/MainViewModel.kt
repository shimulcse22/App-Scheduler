package com.example.appscheduler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appscheduler.datasource.database.AppScheduleTable
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.datasource.model.Timer
import com.example.appscheduler.datasource.repo.AppScheduleLocalDs
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(private val appScheduleLocalDs: AppScheduleLocalDs) : ViewModel() {

    private val getAppSchedule = MutableLiveData<List<AppScheduleTable>>()
    fun onGetAppSchedule() : MutableLiveData<List<AppScheduleTable>> = getAppSchedule

    val installedAppInformation = MutableLiveData<InstalledAppInformation>()


    fun getAllSchedule() {
        appScheduleLocalDs.getSchedules()
            .onEach { getAppSchedule.value = it }
            .launchIn(viewModelScope)
    }

    fun saveAlarmInformation(timer: Timer){
        val appScheduleTable = AppScheduleTable(
            appPackage = installedAppInformation.value?.packageNameOfApp?:"",
            appName = installedAppInformation.value?.appName?:"",
            date = timer.date,
            hour = timer.hour,
            min = timer.min,
        )
        viewModelScope.launch {
            appScheduleLocalDs.addSchedule(appScheduleTable)
        }
    }
}