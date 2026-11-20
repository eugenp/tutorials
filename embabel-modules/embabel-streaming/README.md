# embabel-streaming

Live tests for Embabel Agent streaming with Anthropic. These require an API key and make provider calls.

## Setup

Export your API keys before running:

```bash
export ANTHROPIC_API_KEY=your-anthropic-key
```

## Run

**All tests:**
```bash
$ mvn test -pl embabel-streaming -Dtest=StreamingWithThinkingAndToolingLiveTest

```

**Single test:**
```bash
$ mvn test -pl embabel-streaming -Dtest=StreamingWithThinkingAndToolingLiveTest#whenStreaming_thenReceivesParkingRecommendation
$ mvn test -pl embabel-streaming -Dtest=StreamingWithThinkingAndToolingLiveTest#whenStreamingMultipleScenarios_thenReceivesRecommendationPerScenario
$ mvn test -pl embabel-streaming -Dtest=StreamingWithThinkingAndToolingLiveTest#whenStreamingWithThinking_thenReceivesReasoningAndRecommendation
$ mvn test -pl embabel-streaming -Dtest=StreamingWithThinkingAndToolingLiveTest#whenStreamingWithThinkingAndTooling_thenReceivesRecommendationAndReasoning
```
