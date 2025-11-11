package data.metrics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntryDto(
    @SerialName("agent_id") val agentId: String,
    @SerialName("task_success_rate") val taskSuccessRate: Double,
    @SerialName("tool_use_correctness") val toolUseCorrectness: Double,
    @SerialName("policy_violations") val policyViolations: Double,
    @SerialName("avg_turns") val avgTurns: Double
)
