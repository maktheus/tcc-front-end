package presentation.leaderboard

import data.metrics.LeaderboardEntryDto
import data.metrics.LeaderboardRepository
import domain.metrics.GetLeaderboardUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.test.runTest
import presentation.common.UiState
import kotlin.test.Test
import kotlin.test.assertTrue

private class FakeLeaderboardRepository : LeaderboardRepository(HttpClient(MockEngine {})) {
    override suspend fun getLeaderboard(benchmarkId: String): List<LeaderboardEntryDto> = listOf(
        LeaderboardEntryDto("agent-1", 0.9, 0.8, 0.0, 4.5)
    )
}

class LeaderboardViewModelTest {
    @Test
    fun loadLeaderboardEmitsSuccess() = runTest {
        val vm = LeaderboardViewModel(GetLeaderboardUseCase(FakeLeaderboardRepository()), this)

        vm.loadLeaderboard("benchmark-1")

        assertTrue(vm.leaderboard.value is UiState.Success)
    }
}
