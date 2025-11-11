package domain.runs

import core.Result
import data.runs.RunDto
import data.runs.RunRepository

class GetRunsUseCase(private val repository: RunRepository) {
    suspend operator fun invoke(benchmarkId: String? = null): Result<List<RunDto>> = try {
        Result.Success(repository.getRuns(benchmarkId))
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
