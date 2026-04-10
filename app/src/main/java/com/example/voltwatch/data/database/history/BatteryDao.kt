package com.example.voltwatch.data.database.history

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BatteryDao {

    @Upsert
    suspend fun insert(data: BatteryData)

    // ✅ Get full history (latest first)
    @Query("SELECT * FROM battery_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<BatteryData>>

    // ✅ Get latest battery data (Dashboard ke liye)
    @Query("SELECT * FROM battery_history ORDER BY timestamp DESC LIMIT 1")
    fun getLatestBattery(): Flow<BatteryData?>
}