package core

data class AppConfig(
    val baseUrl: String,
    val defaultHeaders: Map<String, String> = emptyMap()
)

object AppConfigProvider {
    private var cached: AppConfig? = null

    fun provide(): AppConfig = cached ?: AppConfig(
        baseUrl = "http://localhost:8080"
    ).also { cached = it }

    fun override(config: AppConfig) {
        cached = config
    }
}
