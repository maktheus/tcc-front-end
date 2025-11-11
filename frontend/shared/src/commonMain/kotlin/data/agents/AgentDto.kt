package data.agents

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentDto(
    val id: String,
    val name: String,
    val provider: String,
    @SerialName("base_url") val baseUrl: String,
    @SerialName("auth_type") val authType: String,
    val metadata: Map<String, String>? = null
)
