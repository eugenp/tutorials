package com.baeldung.springai.explainableaiagents;

import org.springframework.ai.tool.annotation.ToolParam;

public record AgentThinking(
    @ToolParam(description = """
      Your step-by-step reasoning for why you're calling this tool and what you expect.
      Add evidences why did you decided specific tool to call.
      """, required = true)
    String innerThought,
    @ToolParam(description = "Confidence level (low, medium, high) in this tool choice", required = true)
    String confidence) {
}
