package presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import core.AppConfig
import core.AppConfigProvider
import data.agents.AgentRepository
import data.benchmarks.BenchmarkRepository
import data.metrics.LeaderboardRepository
import data.runs.RunRepository
import domain.agents.GetAgentsUseCase
import domain.benchmarks.GetBenchmarksUseCase
import domain.benchmarks.StartBenchmarkRunUseCase
import domain.metrics.GetLeaderboardUseCase
import domain.runs.GetRunDetailsUseCase
import domain.runs.GetRunsUseCase
import network.createHttpClient
import presentation.agents.AgentsViewModel
import presentation.benchmarks.BenchmarksViewModel
import presentation.leaderboard.LeaderboardViewModel
import presentation.runs.RunsViewModel
import presentation.trace.TraceViewModel

enum class Screen {
    Agents,
    Benchmarks,
    Runs,
    Leaderboard,
    TraceViewer
}

class AppState internal constructor(
    val agentsViewModel: AgentsViewModel,
    val benchmarksViewModel: BenchmarksViewModel,
    val runsViewModel: RunsViewModel,
    val leaderboardViewModel: LeaderboardViewModel,
    val traceViewModel: TraceViewModel
) {
    var currentScreen by mutableStateOf(Screen.Agents)
        private set

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}

@Composable
fun rememberAppState(config: AppConfig = AppConfigProvider.provide()): AppState {
    val coroutineScope = rememberCoroutineScope()
    val client = remember(config) {
        createHttpClient(config.baseUrl, config.defaultHeaders)
    }

    return remember(client, coroutineScope) {
        val agentRepository = AgentRepository(client)
        val benchmarkRepository = BenchmarkRepository(client)
        val runRepository = RunRepository(client)
        val leaderboardRepository = LeaderboardRepository(client)

        val agentsViewModel = AgentsViewModel(GetAgentsUseCase(agentRepository), coroutineScope)
        val startBenchmarkRunUseCase = StartBenchmarkRunUseCase(runRepository)
        val benchmarksViewModel = BenchmarksViewModel(
            GetBenchmarksUseCase(benchmarkRepository),
            startBenchmarkRunUseCase,
            coroutineScope
        )
        val runsViewModel = RunsViewModel(
            startBenchmarkRunUseCase,
            GetRunsUseCase(runRepository),
            GetRunDetailsUseCase(runRepository),
            coroutineScope
        )
        val leaderboardViewModel = LeaderboardViewModel(
            GetLeaderboardUseCase(leaderboardRepository),
            coroutineScope
        )
        val traceViewModel = TraceViewModel()

        AppState(
            agentsViewModel = agentsViewModel,
            benchmarksViewModel = benchmarksViewModel,
            runsViewModel = runsViewModel,
            leaderboardViewModel = leaderboardViewModel,
            traceViewModel = traceViewModel
        )
    }
}
