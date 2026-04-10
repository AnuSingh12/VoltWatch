package com.example.voltwatch.data.database.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_history")
data class BatteryData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "percentage")
    val percentage: Int,
    @ColumnInfo(name = "temperature")
    val temperature: Float,
    @ColumnInfo(name = "voltage")
    val voltage: Int,
    @ColumnInfo(name = "technology")
    val technology: String,
    @ColumnInfo(name = "isCharging")
    val isCharging: Boolean,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)