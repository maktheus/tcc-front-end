package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.metrics.LeaderboardEntryDto
import presentation.common.AppState
import presentation.common.UiState

@Composable
fun LeaderboardScreen(appState: AppState) {
    val viewModel = appState.leaderboardViewModel
    val leaderboardState by viewModel.leaderboard.collectAsState()
    var benchmarkId by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Leaderboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = benchmarkId,
            onValueChange = {
                benchmarkId = it
                if (it.isNotBlank()) {
                    viewModel.loadLeaderboard(it)
                }
            },
            label = { Text("Benchmark ID") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        when (leaderboardState) {
            UiState.Idle -> Text("Informe um benchmark para visualizar o ranking.")
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text((leaderboardState as UiState.Error).message, color = MaterialTheme.colorScheme.error)
            is UiState.Success -> LeaderboardList((leaderboardState as UiState.Success<List<LeaderboardEntryDto>>).data)
        }
    }
}

@Composable
private fun LeaderboardList(entries: List<LeaderboardEntryDto>) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(entries) { entry ->
            Card(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text(entry.agentId, style = MaterialTheme.typography.titleMedium)
                    Text("Sucesso: ${(entry.taskSuccessRate * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
                    Text("Ferramentas: ${(entry.toolUseCorrectness * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
                    Text("Violações: ${entry.policyViolations}", style = MaterialTheme.typography.bodySmall)
                    Text("Turnos médios: ${entry.avgTurns}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
