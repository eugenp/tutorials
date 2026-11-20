package com.baeldung.mcpannotations;

import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CodeReviewPromptsUnitTest {

    private final CodeReviewPrompts prompts = new CodeReviewPrompts();

    @Test
    void givenLanguageAndCode_whenGenerateReviewPrompt_thenReturnsCorrectlyFormattedMessage() {
        // Given
        String language = "Java";
        String code = "System.out.println('Hello');";

        // When
        McpSchema.GetPromptResult result = prompts.generateReviewPrompt(language, code);

        // Then
        assertNotNull(result);
        assertEquals("Code Review", result.description());

        McpSchema.PromptMessage message = result.messages().get(0);
        assertEquals(McpSchema.Role.USER, message.role());

        McpSchema.TextContent textContent = (McpSchema.TextContent) message.content();
        assertTrue(textContent.text().contains("review the following Java code"));
        assertTrue(textContent.text().contains(code));
    }

    @Test
    void givenPartialToken_whenCompleteLanguage_thenReturnsFilteredSuggestions() {
        // Given
        String token = "Ja";
        var argument = new io.modelcontextprotocol.spec.McpSchema.CompleteRequest.CompleteArgument("language", token);

        // When
        List<String> suggestions = prompts.completeLanguage(argument);

        // Then
        assertEquals(1, suggestions.size());
        assertTrue(suggestions.contains("Java"));
        assertFalse(suggestions.contains("Python"));
    }
}