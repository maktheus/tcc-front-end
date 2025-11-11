package presentation.runs

import core.Result
import data.runs.RunDto
import domain.benchmarks.StartBenchmarkRunUseCase
import domain.runs.GetRunDetailsUseCase
import domain.runs.GetRunsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.common.UiState

class RunsViewModel(
    private val startBenchmarkRunUseCase: StartBenchmarkRunUseCase,
    private val getRunsUseCase: GetRunsUseCase,
    private val getRunDetailsUseCase: GetRunDetailsUseCase,
    private val scope: CoroutineScope
) {
    private val _runs = MutableStateFlow<UiState<List<RunDto>>>(UiState.Idle)
    val runs: StateFlow<UiState<List<RunDto>>> = _runs

    private val _selectedRun = MutableStateFlow<UiState<RunDto>>(UiState.Idle)
    val selectedRun: StateFlow<UiState<RunDto>> = _selectedRun

    fun loadRuns(benchmarkId: String? = null) {
        _runs.value = UiState.Loading
        scope.launch {
            when (val result = getRunsUseCase(benchmarkId)) {
                is Result.Success -> _runs.value = UiState.Success(result.value)
                is Result.Failure -> _runs.value = UiState.Error(result.throwable.message ?: "Erro ao carregar runs")
            }
        }
    }

    fun startRuns(benchmarkId: String, agentIds: List<String>) {
        _runs.value = UiState.Loading
        scope.launch {
            when (val result = startBenchmarkRunUseCase(benchmarkId, agentIds)) {
                is Result.Success -> _runs.value = UiState.Success(result.value)
                is Result.Failure -> _runs.value = UiState.Error(result.throwable.message ?: "Erro ao iniciar runs")
            }
        }
    }

    fun loadRunDetails(runId: String) {
        _selectedRun.value = UiState.Loading
        scope.launch {
            when (val result = getRunDetailsUseCase(runId)) {
                is Result.Success -> _selectedRun.value = UiState.Success(result.value)
                is Result.Failure -> _selectedRun.value = UiState.Error(result.throwable.message ?: "Erro ao carregar detalhes")
            }
        }
    }
}
