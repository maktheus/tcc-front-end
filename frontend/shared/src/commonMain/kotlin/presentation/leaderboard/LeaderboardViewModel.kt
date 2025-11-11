package presentation.leaderboard

import core.Result
import data.metrics.LeaderboardEntryDto
import domain.metrics.GetLeaderboardUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.common.UiState

class LeaderboardViewModel(
    private val getLeaderboard: GetLeaderboardUseCase,
    private val scope: CoroutineScope
) {
    private val _leaderboard = MutableStateFlow<UiState<List<LeaderboardEntryDto>>>(UiState.Idle)
    val leaderboard: StateFlow<UiState<List<LeaderboardEntryDto>>> = _leaderboard

    fun loadLeaderboard(benchmarkId: String) {
        _leaderboard.value = UiState.Loading
        scope.launch {
            when (val result = getLeaderboard(benchmarkId)) {
                is Result.Success -> _leaderboard.value = UiState.Success(result.value)
                is Result.Failure -> _leaderboard.value = UiState.Error(result.throwable.message ?: "Erro ao carregar leaderboard")
            }
        }
    }
}
