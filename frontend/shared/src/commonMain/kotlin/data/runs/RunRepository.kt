package data.runs

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RunRepository(private val client: HttpClient) {
    @Serializable
    data class StartRunRequest(
        @SerialName("benchmark_id") val benchmarkId: String,
        @SerialName("agent_ids") val agentIds: List<String>
    )

    suspend fun startRuns(benchmarkId: String, agents: List<String>): List<RunDto> =
        client.post("/runs") {
            contentType(ContentType.Application.Json)
            setBody(StartRunRequest(benchmarkId, agents))
        }.body()

    suspend fun getRuns(benchmarkId: String? = null): List<RunDto> =
        client.get("/runs") {
            if (benchmarkId != null) parameter("benchmark_id", benchmarkId)
        }.body()

    suspend fun getRun(id: String): RunDto = client.get("/runs/$id").body()
}
