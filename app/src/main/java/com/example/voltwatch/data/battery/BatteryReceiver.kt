package com.example.voltwatch.data.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.voltwatch.data.database.history.BatteryData
import com.example.voltwatch.data.utils.BatteryUtils

class BatteryReceiver {
    fun register(
        context: Context,
        onUpdate: (BatteryData) -> Unit
    ): BroadcastReceiver {

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val data = BatteryUtils.getBatteryData(intent)
                onUpdate(data)
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.applicationContext.registerReceiver(receiver, filter)

        return receiver
    }
}