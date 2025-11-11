package presentation.benchmarks

import core.Result
import data.benchmarks.BenchmarkDto
import domain.benchmarks.GetBenchmarksUseCase
import domain.benchmarks.StartBenchmarkRunUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.common.UiState

class BenchmarksViewModel(
    private val getBenchmarks: GetBenchmarksUseCase,
    private val startBenchmarkRun: StartBenchmarkRunUseCase,
    private val scope: CoroutineScope
) {
    private val _benchmarks = MutableStateFlow<UiState<List<BenchmarkDto>>>(UiState.Idle)
    val benchmarks: StateFlow<UiState<List<BenchmarkDto>>> = _benchmarks

    private val _startRunState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val startRunState: StateFlow<UiState<Unit>> = _startRunState

    fun loadBenchmarks() {
        if (_benchmarks.value is UiState.Loading) return
        _benchmarks.value = UiState.Loading
        scope.launch {
            when (val result = getBenchmarks()) {
                is Result.Success -> _benchmarks.value = UiState.Success(result.value)
                is Result.Failure -> _benchmarks.value = UiState.Error(result.throwable.message ?: "Erro ao carregar benchmarks")
            }
        }
    }

    fun startBenchmark(benchmarkId: String, agentIds: List<String>) {
        _startRunState.value = UiState.Loading
        scope.launch {
            when (val result = startBenchmarkRun(benchmarkId, agentIds)) {
                is Result.Success -> _startRunState.value = UiState.Success(Unit)
                is Result.Failure -> _startRunState.value = UiState.Error(result.throwable.message ?: "Erro ao iniciar benchmark")
            }
        }
    }
}
