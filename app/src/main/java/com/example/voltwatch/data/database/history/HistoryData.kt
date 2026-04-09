package com.example.voltwatch.data.database.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_history")
data class BatteryHistoryData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val percentage: Int,
    val temperature: Float,
    val voltage: Int,

    val technology: String,
    val isCharging: Boolean,

    val timestamp: Long
)