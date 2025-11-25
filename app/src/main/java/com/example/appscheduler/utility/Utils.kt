package com.example.appscheduler.utility

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.DiffUtil
import com.example.appscheduler.boradcastreceiver.AlarmReceiver
import com.example.appscheduler.datasource.model.Timer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DiffUtilCallback<T>(
    private val oldData: List<T>,
    private val newData: List<T>,
    private val areItemsTheSame: ((oldItem: T, newItem: T) -> Boolean)? = null,
    private val areContentsTheSame: ((oldItem: T, newItem: T) -> Boolean)? = null,
    private val changedPayload: ((oldItem: T?, newItem: T?) -> Any?)? = null,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame?.invoke(oldData[oldItemPosition], newData[newItemPosition])
            ?: (oldData[oldItemPosition] == newData[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame?.invoke(oldData[oldItemPosition], newData[newItemPosition])
            ?: (oldData[oldItemPosition] == newData[newItemPosition])
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return changedPayload?.invoke(oldData[oldItemPosition], newData[newItemPosition])
            ?: super.getChangePayload(oldItemPosition, newItemPosition)
    }
}


@SuppressLint("ScheduleExactAlarm")
fun scheduleAlarm(context: Context, timer: Timer, title: String, message: String,packageName : String) {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    val calendar = Calendar.getInstance()
    val date = sdf.parse(timer.date)
    calendar.time = date!!
    calendar.set(Calendar.HOUR_OF_DAY, timer.hour)
    calendar.set(Calendar.MINUTE, timer.min)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    val now = Calendar.getInstance()
    if (calendar.before(now)) {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra(TITLE, title)
        putExtra(MESSAGE, message)
        putExtra(TARGET_PACKAGE,packageName)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        timer.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

fun Int?.orZero(): Int = this ?: 0
