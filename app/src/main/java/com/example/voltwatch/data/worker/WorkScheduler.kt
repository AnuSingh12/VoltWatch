package com.example.voltwatch.data.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

fun startBatteryWorker(context: Context) {

    val workRequest =
        PeriodicWorkRequestBuilder<BatteryWorkManger>(
            15, TimeUnit.MINUTES
        ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "battery_worker",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}