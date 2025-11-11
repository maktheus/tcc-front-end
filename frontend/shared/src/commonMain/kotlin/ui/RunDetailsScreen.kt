package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.common.UiState
import data.runs.RunDto

@Composable
fun RunDetailsScreen(state: UiState<RunDto>) {
    when (state) {
        UiState.Idle -> {}
        UiState.Loading -> Text("Carregando detalhes...", modifier = Modifier.padding(16.dp))
        is UiState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
        is UiState.Success -> RunDetailsCard(state.data)
    }
}

@Composable
private fun RunDetailsCard(run: RunDto) {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text("Detalhes da run", style = MaterialTheme.typography.titleMedium)
            Text("Benchmark: ${run.benchmarkId}", style = MaterialTheme.typography.bodyMedium)
            Text("Agente: ${run.agentId}", style = MaterialTheme.typography.bodyMedium)
            Text("Status: ${run.status}", style = MaterialTheme.typography.bodyMedium)
            run.startedAt?.let { Text("Início: $it", style = MaterialTheme.typography.bodySmall) }
            run.finishedAt?.let { Text("Fim: $it", style = MaterialTheme.typography.bodySmall) }
        }
    }
}
