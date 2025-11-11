package domain.benchmarks

import core.Result
import data.runs.RunDto
import data.runs.RunRepository

class StartBenchmarkRunUseCase(private val repository: RunRepository) {
    suspend operator fun invoke(benchmarkId: String, agentIds: List<String>): Result<List<RunDto>> = try {
        Result.Success(repository.startRuns(benchmarkId, agentIds))
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
