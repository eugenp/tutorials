# embabel-streaming

Integration tests for Embabel Agent streaming with OpenAI and Anthropic.

## Setup

Export your API keys before running:

```bash
export ANTHROPIC_API_KEY=your-anthropic-key
```

## Run

**Anthropic tests** (claude-sonnet-4-5):
```bash
mvn test -pl embabel-streaming -P integration

```
