package com.example.voltwatch.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voltwatch.data.database.history.BatteryData
import com.example.voltwatch.data.repository.BatteryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashBoardViewModel(private val repository: BatteryRepository) : ViewModel() {

    companion object {
        private const val TAG = "dashboard_vm"
    }
    private val _batteryInfo = MutableStateFlow(BatteryData(
        percentage = 0,
        temperature = 0f,
        voltage = 0,
        technology = "Unknown",
        isCharging = false,
        timestamp = 0L
    ))
    val batteryInfo = _batteryInfo.asStateFlow()

    private var lastUiPercentage = -1

    fun updateBattery(info: BatteryData) {
        if (info.percentage != lastUiPercentage) {
            lastUiPercentage = info.percentage
            _batteryInfo.value = info
        }
    }

    val latestBattery = repository.getLatestBattery()

//    fun insertBattery(data: BatteryData) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.insert(data)
//        }
//    }

    private var lastPercentage = -1

    fun insertBattery(data: BatteryData) {
        if (data.percentage != lastPercentage) {
            lastPercentage = data.percentage
            viewModelScope.launch(Dispatchers.IO) {
                repository.insert(data)
            }
        }
    }
}