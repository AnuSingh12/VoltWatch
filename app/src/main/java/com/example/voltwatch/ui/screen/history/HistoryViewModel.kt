package com.example.voltwatch.ui.screen.history

import androidx.lifecycle.ViewModel
import com.example.voltwatch.data.repository.BatteryRepository

class HistoryViewModel(
    private val repository: BatteryRepository
) : ViewModel() {

    val history = repository.getAllHistory()
}