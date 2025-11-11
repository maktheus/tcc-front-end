package domain.agents

import core.Result
import data.agents.AgentDto
import data.agents.AgentRepository

class GetAgentsUseCase(private val repository: AgentRepository) {
    suspend operator fun invoke(): Result<List<AgentDto>> = try {
        Result.Success(repository.getAgents())
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
