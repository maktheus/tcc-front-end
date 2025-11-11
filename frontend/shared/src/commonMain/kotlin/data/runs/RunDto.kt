package data.runs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunDto(
    val id: String,
    @SerialName("benchmark_id") val benchmarkId: String,
    @SerialName("agent_id") val agentId: String,
    val status: String,
    @SerialName("started_at") val startedAt: String? = null,
    @SerialName("finished_at") val finishedAt: String? = null
)
