package domain.benchmarks

import core.Result
import data.benchmarks.BenchmarkDto
import data.benchmarks.BenchmarkRepository

class GetBenchmarksUseCase(private val repository: BenchmarkRepository) {
    suspend operator fun invoke(): Result<List<BenchmarkDto>> = try {
        Result.Success(repository.getBenchmarks())
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
