package network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect fun platformEngine(): HttpClientEngineFactory<*>

fun createHttpClient(baseUrl: String, defaultHeaders: Map<String, String> = emptyMap()): HttpClient =
    HttpClient(platformEngine()) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
        }
        defaultRequest {
            url(baseUrl)
            headers {
                defaultHeaders.forEach { (key, value) -> append(key, value) }
            }
            contentType(ContentType.Application.Json)
        }
    }
