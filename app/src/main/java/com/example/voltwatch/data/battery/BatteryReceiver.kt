package com.example.voltwatch.data.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.voltwatch.data.database.history.BatteryData
import com.example.voltwatch.data.utils.BatteryUtils

class BatteryReceiver {

    private var wasCharging: Boolean? = null

    fun register(
        context: Context,
        onUpdate: (BatteryData) -> Unit
    ): BroadcastReceiver {

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val data = BatteryUtils.getBatteryData(intent)
                onUpdate(data)

                if (context != null) {

                    if (wasCharging == null) {
                        wasCharging = data.isCharging
                        return
                    }
                    if (data.isCharging && wasCharging == false) {
                        vibrate(context)
                    }
                    wasCharging = data.isCharging
                }
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(receiver, filter)

        return receiver
    }

    private fun vibrate(context: Context) {
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(
                android.os.VibrationEffect.createOneShot(
                    300,
                    android.os.VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(300)
        }
    }
}

