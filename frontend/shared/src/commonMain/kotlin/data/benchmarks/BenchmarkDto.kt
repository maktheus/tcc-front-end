package data.benchmarks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BenchmarkTaskDto(
    val id: String,
    @SerialName("user_prompt") val userPrompt: String,
    @SerialName("must_call_tool") val mustCallTool: String? = null,
    val constraints: List<String>? = null
)

@Serializable
data class BenchmarkDto(
    val id: String,
    val name: String,
    val domain: String,
    val description: String,
    val tasks: List<BenchmarkTaskDto> = emptyList()
)
