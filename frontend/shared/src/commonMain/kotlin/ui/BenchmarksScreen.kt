package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.agents.AgentDto
import data.benchmarks.BenchmarkDto
import presentation.common.AppState
import presentation.common.UiState

@Composable
fun BenchmarksScreen(appState: AppState) {
    val viewModel = appState.benchmarksViewModel
    val agentsViewModel = appState.agentsViewModel
    val benchmarksState by viewModel.benchmarks.collectAsState()
    val agentsState by agentsViewModel.agents.collectAsState()
    val startRunState by viewModel.startRunState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadBenchmarks()
        agentsViewModel.loadAgents()
    }

    var selectedBenchmark by remember { mutableStateOf<BenchmarkDto?>(null) }
    var selectedAgents by remember { mutableStateOf(setOf<String>()) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(startRunState) {
        if (startRunState is UiState.Success) {
            val result = snackbarHostState.showSnackbar("Benchmark iniciado com sucesso")
            if (result == SnackbarResult.Dismissed) {
                selectedAgents = emptySet()
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Benchmarks", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        SnackbarHost(hostState = snackbarHostState)

        when (benchmarksState) {
            UiState.Idle, UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text((benchmarksState as UiState.Error).message, color = MaterialTheme.colorScheme.error)
            is UiState.Success -> {
                BenchmarkList(
                    benchmarks = (benchmarksState as UiState.Success<List<BenchmarkDto>>).data,
                    selectedBenchmark = selectedBenchmark,
                    onSelect = { selectedBenchmark = it }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        when (agentsState) {
            UiState.Idle, UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text((agentsState as UiState.Error).message, color = MaterialTheme.colorScheme.error)
            is UiState.Success -> AgentSelection(
                agents = (agentsState as UiState.Success<List<AgentDto>>).data,
                selected = selectedAgents,
                onToggle = { agentId ->
                    selectedAgents = if (selectedAgents.contains(agentId)) {
                        selectedAgents - agentId
                    } else {
                        selectedAgents + agentId
                    }
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            enabled = selectedBenchmark != null && selectedAgents.isNotEmpty() && startRunState !is UiState.Loading,
            onClick = {
                selectedBenchmark?.let { benchmark ->
                    viewModel.startBenchmark(benchmark.id, selectedAgents.toList())
                }
            }
        ) {
            if (startRunState is UiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(end = 8.dp).size(16.dp), strokeWidth = 2.dp)
            }
            Text("Iniciar benchmark")
        }
    }
}

@Composable
private fun BenchmarkList(
    benchmarks: List<BenchmarkDto>,
    selectedBenchmark: BenchmarkDto?,
    onSelect: (BenchmarkDto) -> Unit
) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(benchmarks) { benchmark ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onSelect(benchmark) },
                tonalElevation = if (selectedBenchmark?.id == benchmark.id) 4.dp else 0.dp
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(benchmark.name, style = MaterialTheme.typography.titleMedium)
                    Text(benchmark.description, style = MaterialTheme.typography.bodyMedium)
                    Text("Tarefas: ${benchmark.tasks.size}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun AgentSelection(
    agents: List<AgentDto>,
    selected: Set<String>,
    onToggle: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Selecione agentes", style = MaterialTheme.typography.titleMedium)
        agents.forEach { agent ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(agent.name, style = MaterialTheme.typography.bodyLarge)
                    Text(agent.provider, style = MaterialTheme.typography.bodySmall)
                }
                Checkbox(
                    checked = selected.contains(agent.id),
                    onCheckedChange = { onToggle(agent.id) }
                )
            }
        }
    }
}
