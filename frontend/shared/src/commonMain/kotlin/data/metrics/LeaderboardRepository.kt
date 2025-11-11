package data.metrics

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

open class LeaderboardRepository(private val client: HttpClient) {
    open suspend fun getLeaderboard(benchmarkId: String): List<LeaderboardEntryDto> =
        client.get("/leaderboard") {
            parameter("benchmark_id", benchmarkId)
        }.body()
}
