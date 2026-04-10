package com.example.voltwatch.data.utils

import android.content.Intent
import android.os.BatteryManager
import com.example.voltwatch.data.database.history.BatteryData

object BatteryUtils {

    /**
     * return all info related to battery
     * includes percentage, temperature, voltage, charging status
     *
     * @param intent Battery change intent (ACTION_BATTERY_CHANGED)
     * @return BatteryData object that contains all values
     */
    fun getBatteryData(intent: Intent?): BatteryData {

        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val percentage = if (scale > 0) level * 100 / scale else 0

        val temp = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
        val temperature = temp / 10f

        val voltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0
        val technology = intent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unknown"

        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL

        return BatteryData(
            percentage = percentage,
            temperature = temperature,
            voltage = voltage,
            technology = technology,
            isCharging = isCharging,
            timestamp = System.currentTimeMillis()
        )
    }
}