package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Assessment
import androidx.compose.material3.icons.filled.FormatListBulleted
import androidx.compose.material3.icons.filled.Leaderboard
import androidx.compose.material3.icons.filled.Person
import androidx.compose.material3.icons.filled.Timeline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.common.AppState
import presentation.common.Screen
import presentation.common.rememberAppState

@Composable
fun AppRoot(appState: AppState = rememberAppState()) {
    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = appState.currentScreen == Screen.Agents,
                        onClick = { appState.navigateTo(Screen.Agents) },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Agents") }
                )
                NavigationBarItem(
                    selected = appState.currentScreen == Screen.Benchmarks,
                    onClick = { appState.navigateTo(Screen.Benchmarks) },
                    icon = { Icon(Icons.Default.FormatListBulleted, contentDescription = null) },
                    label = { Text("Benchmarks") }
                )
                NavigationBarItem(
                    selected = appState.currentScreen == Screen.Runs,
                    onClick = { appState.navigateTo(Screen.Runs) },
                    icon = { Icon(Icons.Default.Assessment, contentDescription = null) },
                    label = { Text("Runs") }
                )
                NavigationBarItem(
                    selected = appState.currentScreen == Screen.Leaderboard,
                    onClick = { appState.navigateTo(Screen.Leaderboard) },
                    icon = { Icon(Icons.Default.Leaderboard, contentDescription = null) },
                    label = { Text("Leaderboard") }
                )
                    NavigationBarItem(
                        selected = appState.currentScreen == Screen.TraceViewer,
                        onClick = { appState.navigateTo(Screen.TraceViewer) },
                        icon = { Icon(Icons.Default.Timeline, contentDescription = null) },
                        label = { Text("Traces") }
                    )
                }
            }
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                when (appState.currentScreen) {
                    Screen.Agents -> AgentsScreen(appState)
                    Screen.Benchmarks -> BenchmarksScreen(appState)
                    Screen.Runs -> RunsScreen(appState)
                    Screen.Leaderboard -> LeaderboardScreen(appState)
                    Screen.TraceViewer -> TraceViewerScreen(appState)
                }
            }
        }
    }
}
