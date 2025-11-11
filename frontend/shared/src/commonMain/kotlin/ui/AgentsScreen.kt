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
import data.agents.AgentDto
import presentation.common.AppState
import presentation.common.UiState

@Composable
fun AgentsScreen(appState: AppState) {
    val viewModel = appState.agentsViewModel
    val state by viewModel.agents.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadAgents() }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Agentes", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        when (state) {
            UiState.Idle, UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Error -> {
                Text((state as UiState.Error).message, color = MaterialTheme.colorScheme.error)
            }

            is UiState.Success -> {
                AgentList((state as UiState.Success<List<AgentDto>>).data)
            }
        }
    }
}

@Composable
private fun AgentList(agents: List<AgentDto>) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(agents) { agent ->
            Card(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text(agent.name, style = MaterialTheme.typography.titleMedium)
                    Text(agent.provider, style = MaterialTheme.typography.bodyMedium)
                    Text(agent.baseUrl, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
