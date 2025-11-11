package domain.runs

import core.Result
import data.runs.RunDto
import data.runs.RunRepository

class GetRunDetailsUseCase(private val repository: RunRepository) {
    suspend operator fun invoke(id: String): Result<RunDto> = try {
        Result.Success(repository.getRun(id))
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
