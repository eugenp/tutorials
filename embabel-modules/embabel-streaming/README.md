# embabel-streaming

Integration tests for Embabel Agent streaming with OpenAI and Anthropic.

## Setup

Export your API keys before running:

```bash
export OPENAI_API_KEY=your-openai-key
export ANTHROPIC_API_KEY=your-anthropic-key
```

## Run

**OpenAI tests** (gpt-4.1-mini):
```bash
mvn test -pl embabel-streaming-openai
```

**Anthropic tests** (claude-sonnet-4-5):
```bash
mvn test -pl embabel-streaming-anthropic
```
