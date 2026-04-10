package com.example.voltwatch.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.voltwatch.data.repository.BatteryRepository
import kotlin.jvm.java

class DashBoardViewModelFactory(
    private val dashRepo: BatteryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashBoardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashBoardViewModel(
                dashRepo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}