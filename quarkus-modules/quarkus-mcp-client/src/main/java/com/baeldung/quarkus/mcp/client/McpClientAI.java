package com.baeldung.quarkus.mcp.client;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import jakarta.enterprise.context.SessionScoped;


@RegisterAiService
@SessionScoped
public interface McpClientAI {

    @SystemMessage("""
            You are a knowledgeable and helpful assistant powered by Mistral.
            You can answer user questions and provide clear, concise, and accurate information.
            You also have access to a set of tools via an MCP server.

            When using a tool, always convert the tool's response into a natural, human-readable answer.
            If the user's question is unclear, politely ask for clarification.
            If the question does not require tool usage, answer it directly using your own knowledge.

            Always communicate in a friendly and professional manner, and ensure your responses are easy to understand.
            """
    )
    @McpToolBox("default")
    String chat(@UserMessage String question);
}