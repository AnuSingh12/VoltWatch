package com.example.voltwatch.data.repository

import com.example.voltwatch.data.database.history.BatteryDao
import com.example.voltwatch.data.database.history.BatteryData
import kotlinx.coroutines.flow.Flow

class BatteryRepository(
    private val batteryDao: BatteryDao
) {

    suspend fun insert(data: BatteryData) {
        batteryDao.insert(data)
    }

    fun getAllHistory(): Flow<List<BatteryData>> {
        return batteryDao.getAllHistory()
    }

    fun getLatestBattery(): Flow<BatteryData?> {
        return batteryDao.getLatestBattery()
    }
}