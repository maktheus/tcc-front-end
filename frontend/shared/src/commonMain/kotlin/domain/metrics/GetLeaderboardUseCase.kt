package domain.metrics

import core.Result
import data.metrics.LeaderboardEntryDto
import data.metrics.LeaderboardRepository

class GetLeaderboardUseCase(private val repository: LeaderboardRepository) {
    suspend operator fun invoke(benchmarkId: String): Result<List<LeaderboardEntryDto>> = try {
        Result.Success(repository.getLeaderboard(benchmarkId))
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
