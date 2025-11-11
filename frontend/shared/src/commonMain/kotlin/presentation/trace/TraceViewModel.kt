package presentation.trace

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import presentation.common.UiState

class TraceViewModel {
    private val _traceState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val traceState: StateFlow<UiState<Unit>> = _traceState

    fun loadTrace(runId: String) {
        // Placeholder until trace API is available
        _traceState.value = UiState.Success(Unit)
    }
}
