package com.baeldung.mcpannotations;

import io.modelcontextprotocol.spec.McpSchema;
import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeReviewPrompts {

    @McpPrompt(
            name = "review_code",
            description = "Generates a standard code review request"
    )
    public McpSchema.GetPromptResult generateReviewPrompt(
            @McpArg(name = "language", description = "The programming language", required = true) String language,
            @McpArg(name = "code", description = "The code snippet", required = true) String code
    ) {
        String template = """
            Please review the following %s code.\s
            Focus on thread safety and performance:
           \s
            %s
           \s""";

        String content = String.format(template, language, code);

        return new McpSchema.GetPromptResult(
                "Code Review",
                List.of(new McpSchema.PromptMessage(McpSchema.Role.USER, new McpSchema.TextContent(content)))
        );
    }
}
