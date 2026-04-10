package com.example.voltwatch.ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voltwatch.data.database.AppDatabase
import com.example.voltwatch.data.repository.BatteryRepository
import com.example.voltwatch.ui.component.BatteryCard

@Composable
fun HistoryScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = BatteryRepository(database.batteryDao())

    val viewModel: HistoryViewModel = viewModel(
        factory = HistoryViewModelFactory(repository)
    )
    val historyList by viewModel.history.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Battery History",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(historyList) { data ->
                BatteryCard(title = "History", data = data)
            }
        }
        Button(
            onClick = {
                onBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Dashboard")
        }
    }
}