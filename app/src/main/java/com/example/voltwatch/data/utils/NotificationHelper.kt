package com.example.voltwatch.data.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    fun showNotification(context: Context, percentage: Int) {

        val channelId = "battery_alert"

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Battery Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Battery Alert ⚡")
            .setContentText("Battery reached $percentage%")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}