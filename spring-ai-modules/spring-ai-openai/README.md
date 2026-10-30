## Spring AI OpenAI

This module demonstrates Spring AI's integration with the official OpenAI Java SDK. It uses Java 21, Spring Boot 4.1.2, and Spring AI 2.0.2.

`SupportSummaryService` uses `ChatClient`. Spring Boot configures an `OpenAiChatModel`, which calls the provider through the official SDK supplied transitively by `spring-ai-starter-model-openai`.

### Build and Offline Tests

Run these commands from this module's directory:

```bash
mvn clean install -Dgib.enabled=false
```

The configuration tests use a local mock HTTP server and dummy credentials. They verify both provider configurations, the Chat Completions request, and SDK authentication errors. They require no API keys or provider access.

### OpenAI Configuration

```bash
export OPENAI_API_KEY='your-openai-api-key'
mvn spring-boot:run -Dgib.enabled=false
```

OpenAI is the default provider. The example uses `gpt-6-luna`, with reasoning disabled and a maximum of 256 completion tokens. Set `OPENAI_MODEL` to change the model, adjusting model-specific options if necessary. OpenAI API access requires available API credit.

In another terminal, send a short support request:

```bash
curl http://localhost:8080/support/summary \
  -H 'Content-Type: text/plain' \
  --data 'I was charged twice for my subscription. Please refund the duplicate charge.'
```

The response is a one-sentence summary. Its wording can vary between calls.

### Gemini Alternative

```bash
export GEMINI_API_KEY='your-gemini-api-key'
mvn spring-boot:run -Dgib.enabled=false -Dspring-boot.run.profiles=gemini
```

The `gemini` profile selects Google's OpenAI-compatible endpoint and `gemini-3.8-flash`. The application still uses the OpenAI starter and official OpenAI SDK. No OpenAI key is needed for this profile. Set `GEMINI_MODEL` to override the model.

Gemini 3.8 Flash has a free tier, subject to the project's quota and Google's current availability. This profile uses `reasoning-effort: low` and a larger token allowance because Gemini's thinking behavior differs from OpenAI's. See [Google's compatibility documentation](https://ai.google.dev/gemini-api/docs/openai) and [pricing](https://ai.google.dev/gemini-api/docs/pricing).

### Live Tests

The parent POM excludes `*LiveTest` from ordinary builds. Run either provider explicitly:

```bash
mvn test -Dgib.enabled=false -Dtest=OpenAiSupportSummaryLiveTest
mvn test -Dgib.enabled=false -Dtest=GeminiSupportSummaryLiveTest
```

Each live test sends one real request when its corresponding environment variable is set; otherwise, JUnit skips it. Live tests log the actual summary and check that it is nonblank. They do not assert an exact generated sentence. The example disables automatic SDK retries and sets a request timeout.

Keep API credentials in environment variables, outside source control.

### Migration Scope

Spring AI 2.0 incorporates the official SDK into its standard OpenAI integration. The following changes apply when moving from the separate SDK integration in Spring AI 1.1.x to this module's Spring AI 2.0.2 configuration:

| Separate SDK integration | Spring AI 2.0.2 |
| --- | --- |
| `spring-ai-starter-model-openai-sdk` | `spring-ai-starter-model-openai` |
| `spring.ai.openai-sdk.api-key` | `spring.ai.openai.api-key` |
| `spring.ai.openai-sdk.base-url` | `spring.ai.openai.base-url` |
| `spring.ai.openai-sdk.chat.options.model` | `spring.ai.openai.chat.model` |
| `org.springframework.ai.openaisdk.OpenAiSdkChatModel` | `org.springframework.ai.openai.OpenAiChatModel` |
| `org.springframework.ai.openaisdk.OpenAiSdkChatOptions` | `org.springframework.ai.openai.OpenAiChatOptions` |

The new model implementation uses the official SDK transitively; no separate SDK starter or direct `openai-java` dependency is needed. The application can continue calling `ChatClient`.

These changes target the former separate SDK module. Existing users of the standard OpenAI module keep their `spring.ai.openai.*` configuration and do not need that starter/class migration. See the [Spring AI upgrade notes](https://docs.spring.io/spring-ai/reference/upgrade-notes.html).
