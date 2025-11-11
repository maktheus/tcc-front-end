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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.runs.RunDto
import presentation.common.AppState
import presentation.common.UiState
import androidx.compose.material3.ExperimentalMaterial3Api

@Composable
fun RunsScreen(appState: AppState) {
    val viewModel = appState.runsViewModel
    val runsState by viewModel.runs.collectAsState()
    val selectedRunState by viewModel.selectedRun.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadRuns() }

    Column(Modifier.fillMaxSize()) {
        RunLaunchScreen(viewModel, runsState)
        Spacer(Modifier.height(8.dp))
        when (runsState) {
            UiState.Idle, UiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is UiState.Error -> Text((runsState as UiState.Error).message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
            is UiState.Success -> RunList(
                runs = (runsState as UiState.Success<List<RunDto>>).data,
                onSelect = { viewModel.loadRunDetails(it.id) }
            )
        }
        Spacer(Modifier.height(16.dp))
        RunDetailsScreen(selectedRunState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RunList(runs: List<RunDto>, onSelect: (RunDto) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        items(runs) { run ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), onClick = { onSelect(run) }) {
                Column(Modifier.padding(16.dp)) {
                    Text("Run ${run.id}", style = MaterialTheme.typography.titleMedium)
                    Text("Benchmark: ${run.benchmarkId}", style = MaterialTheme.typography.bodyMedium)
                    Text("Agente: ${run.agentId}", style = MaterialTheme.typography.bodySmall)
                    Text("Status: ${run.status}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
