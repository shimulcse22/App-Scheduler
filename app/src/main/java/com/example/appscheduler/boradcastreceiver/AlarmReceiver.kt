package com.example.appscheduler.boradcastreceiver

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.appscheduler.boradcastreceiver.LaunchAndStopAlarmReceiver
import com.example.appscheduler.boradcastreceiver.StopAlarmReceiver
import com.example.appscheduler.utility.MESSAGE
import com.example.appscheduler.utility.SCHEDULE_CHANNEL
import com.example.appscheduler.utility.SCHEDULE_NOTIFICATION
import com.example.appscheduler.utility.STOP
import com.example.appscheduler.utility.TARGET_PACKAGE
import com.example.appscheduler.utility.TITLE

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        var ringtone: Ringtone? = null
    }
    override fun onReceive(context: Context, intent: Intent) {

        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (ringtone == null) {
            ringtone = RingtoneManager.getRingtone(context, alarmUri)
            ringtone?.play()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone?.isLooping = true
        }


        val title = intent.getStringExtra(TITLE) ?: "Alarm"
        val message = intent.getStringExtra(MESSAGE) ?: "It's time!"
        val targetPackage = intent.getStringExtra(TARGET_PACKAGE)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            SCHEDULE_CHANNEL,
            SCHEDULE_NOTIFICATION,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val stopIntent = Intent(context, StopAlarmReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val openAndStopIntent = Intent(context, LaunchAndStopAlarmReceiver::class.java).apply {
            putExtra(TARGET_PACKAGE, targetPackage)
        }

        val launchAndAlarmStopIntent = PendingIntent.getBroadcast(
            context,
            101,
            openAndStopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, SCHEDULE_CHANNEL)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(launchAndAlarmStopIntent)
            .addAction(R.drawable.ic_menu_close_clear_cancel, STOP, stopPendingIntent)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}