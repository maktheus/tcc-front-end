package snapshots

import data.agents.AgentDto
import data.benchmarks.BenchmarkDto
import data.benchmarks.BenchmarkTaskDto
import data.metrics.LeaderboardEntryDto
import data.runs.RunDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

private val json = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
    encodeDefaults = false
    ignoreUnknownKeys = true
}

class DtoSnapshotTest {

    @Test
    fun agentDtoMatchesSnapshot() {
        val dto = AgentDto(
            id = "agent-1",
            name = "Benchmark Agent",
            provider = "openai",
            base_url = "https://api.example.com",
            auth_type = "bearer",
            metadata = linkedMapOf(
                "tier" to "enterprise",
                "contact" to "ops@example.com"
            )
        )

        val snapshot = """
            {
              "id": "agent-1",
              "name": "Benchmark Agent",
              "provider": "openai",
              "base_url": "https://api.example.com",
              "auth_type": "bearer",
              "metadata": {
                "tier": "enterprise",
                "contact": "ops@example.com"
              }
            }
        """.trimIndent()

        assertEquals(snapshot, json.encodeToString(dto))
        assertEquals(dto, json.decodeFromString(snapshot))
    }

    @Test
    fun benchmarkDtoMatchesSnapshot() {
        val dto = BenchmarkDto(
            id = "benchmark-42",
            name = "Support Benchmark",
            domain = "support",
            description = "Validates support scenarios",
            tasks = listOf(
                BenchmarkTaskDto(
                    id = "task-1",
                    user_prompt = "Reset password flow",
                    must_call_tool = "ticketing",
                    constraints = listOf("answer in pt-BR", "respect compliance policy")
                ),
                BenchmarkTaskDto(
                    id = "task-2",
                    user_prompt = "Simulate refund negotiation"
                )
            )
        )

        val snapshot = """
            {
              "id": "benchmark-42",
              "name": "Support Benchmark",
              "domain": "support",
              "description": "Validates support scenarios",
              "tasks": [
                {
                  "id": "task-1",
                  "user_prompt": "Reset password flow",
                  "must_call_tool": "ticketing",
                  "constraints": [
                    "answer in pt-BR",
                    "respect compliance policy"
                  ]
                },
                {
                  "id": "task-2",
                  "user_prompt": "Simulate refund negotiation"
                }
              ]
            }
        """.trimIndent()

        assertEquals(snapshot, json.encodeToString(dto))
        assertEquals(dto, json.decodeFromString(snapshot))
    }

    @Test
    fun runDtoMatchesSnapshot() {
        val dto = RunDto(
            id = "run-99",
            benchmark_id = "benchmark-42",
            agent_id = "agent-1",
            status = "running",
            started_at = "2024-01-01T10:00:00Z",
            finished_at = null
        )

        val snapshot = """
            {
              "id": "run-99",
              "benchmark_id": "benchmark-42",
              "agent_id": "agent-1",
              "status": "running",
              "started_at": "2024-01-01T10:00:00Z"
            }
        """.trimIndent()

        assertEquals(snapshot, json.encodeToString(dto))
        assertEquals(dto, json.decodeFromString(snapshot))
    }

    @Test
    fun leaderboardDtoMatchesSnapshot() {
        val dto = LeaderboardEntryDto(
            agent_id = "agent-1",
            task_success_rate = 0.92,
            tool_use_correctness = 0.87,
            policy_violations = 0.0,
            avg_turns = 8.5
        )

        val snapshot = """
            {
              "agent_id": "agent-1",
              "task_success_rate": 0.92,
              "tool_use_correctness": 0.87,
              "policy_violations": 0.0,
              "avg_turns": 8.5
            }
        """.trimIndent()

        assertEquals(snapshot, json.encodeToString(dto))
        assertEquals(dto, json.decodeFromString(snapshot))
    }
}
