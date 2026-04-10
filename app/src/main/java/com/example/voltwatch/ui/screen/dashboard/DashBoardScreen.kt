package com.example.voltwatch.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voltwatch.data.battery.BatteryReceiver
import com.example.voltwatch.data.database.AppDatabase
import com.example.voltwatch.data.repository.BatteryRepository
import com.example.voltwatch.ui.component.BatteryCard


@Composable
fun DashBoardScreen() {

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = BatteryRepository(
        batteryDao = database.historyDao()
    )
    val viewmodel: DashBoardViewModel = viewModel(
        factory = DashBoardViewModelFactory(repository)
    )

    val batteryData by viewmodel.batteryInfo.collectAsState()
    val latestData by viewmodel.latestBattery.collectAsState(initial = null)

    DisposableEffect(Unit) {

        val receiver = BatteryReceiver(context)

        val broadcast = receiver.register { data ->

            viewmodel.updateBattery(data)

            viewmodel.insertBattery(data)
        }

        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "⚡ VoltWatch Dashboard",
            style = MaterialTheme.typography.headlineSmall
        )

        // 🔴 LIVE DATA CARD
        BatteryCard(title = "Live Battery", data = batteryData)

        // 🟢 LATEST STORED DATA
        latestData?.let {
            BatteryCard(title = "Last Saved", data = it)
        }
    }

}

