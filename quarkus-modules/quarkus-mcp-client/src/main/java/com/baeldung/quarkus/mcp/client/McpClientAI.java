package com.baeldung.quarkus.mcp.client;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.SessionScoped;


@RegisterAiService
@SessionScoped
public interface McpClientAI {

    @SystemMessage("""
            You are a helpful assistant that can answer questions and provide useful answers to the user.
            In addition you have access to a set of tools through an MCP server.
 
            Use the tools only if needed to answer questions from the user.
            Convert any tool response into a human readable format and provide answers in natural language.
            """
    )
    String chat(@UserMessage String question);
}