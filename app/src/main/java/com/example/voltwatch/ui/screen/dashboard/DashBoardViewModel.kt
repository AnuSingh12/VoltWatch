package com.example.voltwatch.ui.screen.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voltwatch.data.database.history.BatteryData
import com.example.voltwatch.data.preferences.AlertPreference
import com.example.voltwatch.data.repository.BatteryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashBoardViewModel(private val repository: BatteryRepository) : ViewModel() {
    companion object {
        private const val TAG = "dashboard_vm"
    }
    private val _batteryInfo = MutableStateFlow(
        BatteryData(
            percentage = 0,
            temperature = 0f,
            voltage = 0,
            technology = "Unknown",
            isCharging = false,
            timestamp = 0L
        )
    )
    val batteryInfo = _batteryInfo.asStateFlow()
    fun updateBattery(info: BatteryData) {
        _batteryInfo.value = info
    }

    fun saveTarget(context: Context, value: Int) {
        AlertPreference.saveTarget(context, value)
    }

    fun getTarget(context: Context): Int {
        return AlertPreference.getTarget(context)
    }

    ///////////////////////////////////////////////////////////////////////////
    // for testing
    ///////////////////////////////////////////////////////////////////////////
    fun insertBattery(data: BatteryData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(data)
        }
    }
}