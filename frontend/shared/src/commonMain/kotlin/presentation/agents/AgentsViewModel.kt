package presentation.agents

import core.Result
import data.agents.AgentDto
import domain.agents.GetAgentsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.common.UiState

class AgentsViewModel(
    private val getAgents: GetAgentsUseCase,
    private val scope: CoroutineScope
) {
    private val _agents = MutableStateFlow<UiState<List<AgentDto>>>(UiState.Idle)
    val agents: StateFlow<UiState<List<AgentDto>>> = _agents

    fun loadAgents() {
        if (_agents.value is UiState.Loading) return
        _agents.value = UiState.Loading
        scope.launch {
            when (val result = getAgents()) {
                is Result.Success -> _agents.value = UiState.Success(result.value)
                is Result.Failure -> _agents.value = UiState.Error(result.throwable.message ?: "Erro desconhecido")
            }
        }
    }
}
