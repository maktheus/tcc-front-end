package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.common.UiState
import presentation.runs.RunsViewModel

@Composable
fun RunLaunchScreen(
    viewModel: RunsViewModel,
    runsState: UiState<*>,
    modifier: Modifier = Modifier
) {
    var benchmarkId by remember { mutableStateOf("") }
    var agentIdsInput by remember { mutableStateOf("") }

    Column(modifier.padding(16.dp)) {
        Text("Disparar benchmark manualmente", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = benchmarkId,
            onValueChange = { benchmarkId = it },
            label = { Text("Benchmark ID") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = agentIdsInput,
            onValueChange = { agentIdsInput = it },
            label = { Text("Agent IDs (separados por vírgula)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Row {
            Button(
                enabled = benchmarkId.isNotBlank() && agentIdsInput.isNotBlank(),
                onClick = {
                    val agentIds = agentIdsInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    if (agentIds.isNotEmpty()) {
                        viewModel.startRuns(benchmarkId.trim(), agentIds)
                    }
                }
            ) {
                if (runsState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(end = 8.dp).size(16.dp), strokeWidth = 2.dp)
                }
                Text("Iniciar")
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = { viewModel.loadRuns() }, modifier = Modifier.padding(start = 8.dp)) {
                Text("Atualizar lista")
            }
        }
    }
}
