package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.common.AppState
import presentation.common.UiState

@Composable
fun TraceViewerScreen(appState: AppState) {
    val traceState by appState.traceViewModel.traceState.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Trace Viewer", style = MaterialTheme.typography.headlineMedium)
        when (traceState) {
            UiState.Idle -> Text("Selecione uma run para visualizar o trace.", modifier = Modifier.padding(top = 16.dp))
            UiState.Loading -> Text("Carregando trace...", modifier = Modifier.padding(top = 16.dp))
            is UiState.Error -> Text((traceState as UiState.Error).message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
            is UiState.Success -> Text("Trace disponível para análise.", modifier = Modifier.padding(top = 16.dp))
        }
    }
}
