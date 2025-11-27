package com.example.appscheduler.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appscheduler.datasource.database.AppScheduleTable
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.datasource.model.Timer
import com.example.appscheduler.datasource.repo.AppScheduleLocalDs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(private val appScheduleLocalDs: AppScheduleLocalDs) : ViewModel() {

    private val getAppSchedule = MutableLiveData<List<AppScheduleTable>>()
    fun onGetAppSchedule() : MutableLiveData<List<AppScheduleTable>> = getAppSchedule

    val getAppScheduleByPackage = MutableLiveData<AppScheduleTable?>()
    fun onGetAppScheduleByPackage() : MutableLiveData<AppScheduleTable?> = getAppScheduleByPackage

    val installedAppInformation = MutableLiveData<InstalledAppInformation>()


    fun getAllSchedule() {
        viewModelScope.launch {
            val scheduleList = appScheduleLocalDs.getSchedules()
            getAppSchedule.postValue(scheduleList)
        }
    }

    fun saveAlarmInformation(timer: Timer){
        viewModelScope.launch {
            appScheduleLocalDs.addSchedule(installedAppInformation.value,timer)
        }
    }

    fun getAppScheduleByPackageName(packageName : String?) {
        packageName ?: return

        viewModelScope.launch {
            val schedule = appScheduleLocalDs.getAppScheduleByPackageName(packageName)
                .first()
            Log.e("TAG", "getAppScheduleByPackageName 1: $schedule", )
            getAppScheduleByPackage.value = schedule

        }
    }

    fun cancelSchedule() {
        viewModelScope.launch {
            appScheduleLocalDs.updateSchedule(
                getAppScheduleByPackage.value?.copy(
                    executionInformation = "Canceled"
                )
            )
        }
    }

    fun updateSchedule(timer: Timer){
        viewModelScope.launch {
            appScheduleLocalDs.updateSchedule(getAppScheduleByPackage.value?.copy(
                date = timer.date,
                hour = timer.hour,
                min = timer.min,
            ))
        }
    }
}