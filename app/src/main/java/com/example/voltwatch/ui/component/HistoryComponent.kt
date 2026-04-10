package com.example.voltwatch.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voltwatch.data.database.history.BatteryData

@Composable
fun BatteryCard(title: String, data: BatteryData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text("Percentage: ${data.percentage}%")
            Text("Temperature: ${data.temperature} °C")
            Text("Voltage: ${data.voltage} mV")
            Text("Charging: ${if (data.isCharging) "Yes" else "No"}")
            Text("Tech: ${data.technology}")
        }
    }
}