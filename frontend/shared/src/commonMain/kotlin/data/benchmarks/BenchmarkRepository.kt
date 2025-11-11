package data.benchmarks

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BenchmarkRepository(private val client: HttpClient) {
    suspend fun getBenchmarks(): List<BenchmarkDto> = client.get("/benchmarks").body()

    suspend fun getBenchmark(id: String): BenchmarkDto = client.get("/benchmarks/$id").body()
}
