package presentation.agents

import data.agents.AgentDto
import data.agents.AgentRepository
import domain.agents.GetAgentsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.test.runTest
import presentation.common.UiState
import kotlin.test.Test
import kotlin.test.assertTrue

private class FakeAgentRepository : AgentRepository(HttpClient(MockEngine {})) {
    override suspend fun getAgents(): List<AgentDto> = listOf(
        AgentDto("1", "Agent A", "Provider", "http://base", "none")
    )
}

class AgentsViewModelTest {
    @Test
    fun loadAgentsEmitsSuccess() = runTest {
        val vm = AgentsViewModel(GetAgentsUseCase(FakeAgentRepository()), this)

        vm.loadAgents()

        assertTrue(vm.agents.value is UiState.Success)
    }
}
