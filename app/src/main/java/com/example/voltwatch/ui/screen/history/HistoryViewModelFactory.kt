package com.example.voltwatch.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.voltwatch.data.repository.BatteryRepository

class HistoryViewModelFactory(
    private val repository: BatteryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}