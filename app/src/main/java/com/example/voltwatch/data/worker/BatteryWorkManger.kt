package com.example.voltwatch.data.worker

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.voltwatch.data.database.AppDatabase
import com.example.voltwatch.data.preferences.AlertPreference
import com.example.voltwatch.data.repository.BatteryRepository
import com.example.voltwatch.data.utils.BatteryUtils
import com.example.voltwatch.data.utils.NotificationHelper

class BatteryWorkManger(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    /**
     * This function is automatically triggered by WorkManager.
     * It fetches the latest battery data and stores it in the database.
     *
     * @return Result.success() if the operation completes successfully
     */
    override suspend fun doWork(): Result {

        val context = applicationContext

        val database = AppDatabase.getDatabase(context)
        val repository = BatteryRepository(database.batteryDao())

        val intent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        val data = BatteryUtils.getBatteryData(intent)

        val target = AlertPreference.getTarget(context)
        val isTriggered = AlertPreference.isTriggered(context)

        if (data.isCharging) {
            AlertPreference.resetTriggered(context)
        }

        if (target != -1 && data.percentage == target && !isTriggered) {
            NotificationHelper.showNotification(context, data.percentage)
            AlertPreference.setTriggered(context)
        }

        repository.insert(data)

        return Result.success()
    }
}