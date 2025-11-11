package domain.agents

import core.Result
import data.agents.AgentDto
import data.agents.AgentRepository

class CreateAgentUseCase(private val repository: AgentRepository) {
    suspend operator fun invoke(agent: AgentDto): Result<AgentDto> = try {
        Result.Success(repository.createAgent(agent))
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
