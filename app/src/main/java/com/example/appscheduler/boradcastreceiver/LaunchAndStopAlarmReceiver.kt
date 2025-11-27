package com.example.appscheduler.boradcastreceiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.appscheduler.utility.TARGET_PACKAGE
import com.example.appscheduler.utility.orEmpty

class LaunchAndStopAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        AlarmReceiver.ringtone?.stop()
        AlarmReceiver.ringtone = null

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(1001)

        val targetPackage = intent.getStringExtra(TARGET_PACKAGE)

        val launchIntent = context.packageManager.getLaunchIntentForPackage(targetPackage.orEmpty())
        launchIntent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(launchIntent)
    }
}