# TCC Frontend (Compose Multiplatform)

Este módulo contém o painel de benchmark de agentes implementado com Compose Multiplatform. O código é organizado em módulos multiplataforma (`shared`) e inicializações específicas para Android, Desktop e Web.

## Módulos

- **shared** – lógica compartilhada (modelos, repositórios, use cases, view models e telas).
- **androidApp** – aplicativo Android que inicializa `AppRoot()`.
- **desktopApp** – aplicação desktop com janela Compose.
- **webApp** – entrada para execução web usando `CanvasBasedWindow`.

## Estrutura principal (`shared`)

- `core` – configuração da aplicação e helpers de `Result`.
- `network` – fábrica do `HttpClient` configurada com `ContentNegotiation` e engines específicas por plataforma.
- `data` – DTOs e repositórios alinhados com os microserviços em Go.
- `domain` – use cases que encapsulam o acesso aos repositórios.
- `presentation` – view models com `StateFlow` e estados `UiState`.
- `ui` – telas Compose reutilizáveis.

## Testes

Os testes de exemplo estão em `shared/src/commonTest`, garantindo que os view models emitam `UiState.Success` ao consumir repositórios fake.

Para executar os testes multiplataforma:

```bash
cd frontend
./gradlew :shared:check
```
