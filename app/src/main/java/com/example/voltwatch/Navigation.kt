package com.example.voltwatch

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.voltwatch.ui.navigation.DashBoardKey
import com.example.voltwatch.ui.navigation.HistoryKey
import com.example.voltwatch.ui.screen.dashboard.DashBoardScreen
import com.example.voltwatch.ui.screen.history.HistoryScreen

@Composable
fun Navigation() {

    val backStack = remember { mutableStateListOf<Any>(DashBoardKey) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->

            when (key) {

                is DashBoardKey -> NavEntry(key) {
                    DashBoardScreen(
                        onNextScreen = { backStack.add(HistoryKey) }
                    )
                }

                is HistoryKey -> NavEntry(key) {
                    HistoryScreen(
                        onBack = { backStack.removeLastOrNull()}
                    )
                }

                else -> NavEntry(Unit) {
                    Text("Unknown")
                }
            }
        }
    )
}
