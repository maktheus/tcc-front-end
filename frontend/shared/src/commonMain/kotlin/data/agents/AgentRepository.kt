package data.agents

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class AgentRepository(private val client: HttpClient) {
    open suspend fun getAgents(): List<AgentDto> = client.get("/agents").body()

    open suspend fun createAgent(agent: AgentDto): AgentDto =
        client.post("/agents") {
            contentType(ContentType.Application.Json)
            setBody(agent)
        }.body()
}
