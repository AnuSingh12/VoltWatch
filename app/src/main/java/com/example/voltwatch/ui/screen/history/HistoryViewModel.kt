package com.example.voltwatch.ui.screen.history

import android.R.attr.data
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voltwatch.data.database.history.BatteryData
import com.example.voltwatch.data.repository.BatteryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: BatteryRepository): ViewModel() {
    companion object {
        private const val TAG = "history_vm"
    }

    private val _history = MutableStateFlow<List<BatteryData>>(emptyList())
    val history = _history.asStateFlow()

    fun readAllData() {
        viewModelScope.launch {
            repository.getAllHistory().collect { data ->
                _history.value = data
            }
            Log.d(TAG, "$data")
        }
    }
}