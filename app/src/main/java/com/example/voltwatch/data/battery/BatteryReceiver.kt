package com.example.voltwatch.data.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.example.voltwatch.data.database.history.BatteryData

class BatteryReceiver(private val context: Context) {

    fun register(onUpdate: (BatteryData) -> Unit): BroadcastReceiver {

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
                val percentage = level * 100 / scale

                val temp = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
                val temperature = temp / 10f

                val voltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0

                val technology = intent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unknown"

                val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

                val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL

                val data = BatteryData(
                    percentage = percentage,
                    temperature = temperature,
                    voltage = voltage,
                    technology = technology,
                    isCharging = isCharging,
                    timestamp =  System.currentTimeMillis()
                )

                onUpdate(data)
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(receiver, filter)

        return receiver
    }
}