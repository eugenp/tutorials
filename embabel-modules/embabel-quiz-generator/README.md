# embabel-quiz-generator

An Embabel agent that reads a blog post and generates a multiple choice quiz from it. The blog is fetched through the
`fetch` MCP server, served by the Docker MCP gateway. The LLM is Anthropic Claude Opus 4.8.

## Setup

- Java 21 and Maven
- An Anthropic API key:

```bash
export ANTHROPIC_API_KEY=your-anthropic-key
```

- Docker with the MCP Toolkit:
  - Docker Desktop (macOS, Windows, Linux): the MCP Toolkit is built in
  - Docker Engine (Linux): install the `docker mcp` CLI plugin

The gateway is configured in `src/main/resources/application-docker-desktop.yaml`. It starts only the `fetch` server
(`--servers=fetch`). The `docker-desktop` profile is activated in `Application.java`.

- On Docker Desktop, it works as is.
- On Docker Engine, uncomment the `env` block (`DOCKER_MCP_IN_CONTAINER: "1"`) in that file, so the gateway doesn't
  wait for Docker Desktop on startup.

## Run

```bash
$ cd embabel-modules/embabel-quiz-generator
$ mvn spring-boot:run
```

The application starts the Embabel shell:

```
embabel>
```

## Test

Use `x` (short for `execute`) with the URL of a blog post:

```
embabel> x "Generate a quiz from https://www.baeldung.com/java-record-keyword"
```

Embabel picks the `quizzard` agent and runs its plan:
1. `fetchBlogContent` reads the blog with the `fetch` tool
2. `generateQuiz` writes the quiz

The result is printed as JSON:

```
{
  "questions" : [ {
    "question" : "In which Java version were records standardized (no longer a preview feature)?",
    "options" : [ "Java 14", "Java 15", "Java 16", "Java 17" ],
    "correctAnswer" : "Java 16"
  }, ... ]
}
```

At the end, the shell prints the LLM usage, the cost, and the tool usage.

## Check MCP connectivity

### At startup

```
Application - The following 1 profile is active: "docker-desktop"
StdioClientTransport - MCP server started
StdioClientTransport - STDERR Message received: - Those servers are enabled: fetch
StdioClientTransport - STDERR Message received:   > fetch: (1 tools) (1 prompts)
StdioClientTransport - STDERR Message received: > 1 tools listed in ...
LifecycleInitializer - Server response with Protocol: ... Info: Implementation[name=Docker AI MCP Gateway, ...]
Embabel - Deployed agent quizzard
```

### During `x "..."`

```
(fetchBlogContent) starting tool loop [fetch] max=20
(fetchBlogContent) calling tool fetch({"url":"https://www.baeldung.com/java-record-keyword"})
StdioClientTransport - STDERR Message received:   > Calling tool fetch took: ...
```

Long pages come back in parts, so `fetch` may be called several times with `start_index` 5000, 10000, and so on.

### After the result

```
Tool usage:
    ToolStats(name=fetch, calls=3, avgResponseTime=... ms, failures=0)
```

### Signs of a problem

| Log | Meaning |
|---|---|
| `0 tools listed` | The `fetch` server is not available to the gateway |
| A pause of about 30 seconds after `Reading configuration...` | Docker Engine without `DOCKER_MCP_IN_CONTAINER: "1"` |
| `ToolStats(name=fetch, ... failures=N)` with N > 0 | `fetch` calls failed, for example because the URL could not be reached |

`fetch: Warning: node executable not found, reverting to pure-Python mode` comes from inside the `mcp/fetch`
container and is harmless.
