package com.example.appscheduler.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Timer(
    val date: String,
    val hour: Int,
    val min: Int
) : Parcelable{
    override fun toString(): String {
        return "$date ${"%02d".format(hour)}:${"%02d".format(min)}"
    }
}
