package com.example.voltwatch.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voltwatch.data.battery.BatteryReceiver
import com.example.voltwatch.data.database.AppDatabase
import com.example.voltwatch.data.repository.BatteryRepository
import com.example.voltwatch.ui.component.BatteryCircle
import com.example.voltwatch.ui.component.InfoCard

private const val TAG = "dashboard_screen"
@Composable
fun DashBoardScreen(
    onNextScreen: () -> Unit
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = BatteryRepository(
        batteryDao = database.batteryDao()
    )
    val viewmodel: DashBoardViewModel = viewModel(
        factory = DashBoardViewModelFactory(repository)
    )
    val batteryData by viewmodel.batteryInfo.collectAsState()
    var sliderValue by remember { mutableStateOf(20f) }
    val scroll = rememberScrollState()

    DisposableEffect(Unit) {

        val receiver = BatteryReceiver()
        val broadcast = receiver.register(context) { data ->
            viewmodel.updateBattery(data)
        }
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }

    LaunchedEffect(Unit) {
        val saved = viewmodel.getTarget(context)
        if (saved != -1) {
            sliderValue = saved.toFloat()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "VoltWatch",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BatteryCircle(data = batteryData)

            InfoCard("Temperature", "${batteryData.temperature} °C")
            InfoCard("Voltage", "${batteryData.voltage} mV")
            InfoCard("Technology", batteryData.technology)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 0f..100f,
                steps = 4
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(0, 20, 40, 60, 80, 100).forEach {
                    Text("$it")
                }
            }
            Text(
                text = "Alert: ${sliderValue.toInt()}%",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(
                onClick = {
                    viewmodel.saveTarget(context, sliderValue.toInt())
                    Log.d(TAG, "Saved Target: ${sliderValue.toInt()}")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Alert")
            }

            Button(
                onClick = {
                    onNextScreen()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View History")
            }
        }
    }
}

