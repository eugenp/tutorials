# embabel-streaming

Integration tests for Embabel Agent streaming with OpenAI and Anthropic.

## Setup

Export your API keys before running:

```bash
export ANTHROPIC_API_KEY=your-anthropic-key
```

## Run

**All tests:**
```bash
$ mvn test -pl embabel-streaming -P integration

```

**Single test:**
```bash
$ mvn test -pl embabel-streaming -P integration -Dtest=StreamingWithThinkingAndToolingIntegrationTest#whenStreaming_thenReceivesParkingRecommendation
$ mvn test -pl embabel-streaming -P integration -Dtest=StreamingWithThinkingAndToolingIntegrationTest#whenStreamingMultipleScenarios_thenReceivesRecommendationPerScenario
$ mvn test -pl embabel-streaming -P integration -Dtest=StreamingWithThinkingAndToolingIntegrationTest#whenStreamingWithThinking_thenReceivesReasoningAndRecommendation
$ mvn test -pl embabel-streaming -P integration -Dtest=StreamingWithThinkingAndToolingIntegrationTest#whenStreamingWithThinkingAndTooling_thenReceivesRecommendationAndReasoning
```
