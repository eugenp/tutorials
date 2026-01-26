package com.baeldung.springai.mcp.server;

import io.modelcontextprotocol.spec.McpSchema;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springaicommunity.mcp.context.McpSyncRequestContext;
import org.springaicommunity.mcp.context.StructuredElicitResult;
import org.springframework.stereotype.Component;

@Component
class AuthorRepository {

    @McpTool(description = "Get Baeldung author details using an article title")
    Author getAuthorByArticleTitle(
        @McpToolParam(description = "Title/name of the article") String articleTitle,
        @McpToolParam(required = false, description = "Name of user requesting author information") String username,
        @McpToolParam(required = false, description = "Reason for requesting author information") String reason,
        McpSyncRequestContext requestContext
    ) {
        if (isPremiumArticle(articleTitle)) {
            if ((isBlank(username) || isBlank(reason)) && requestContext.elicitEnabled()) {
                StructuredElicitResult<PremiumArticleAccessRequest> elicitResult = requestContext.elicit(
                    e -> e.message("Baeldung username and reason required."),
                    PremiumArticleAccessRequest.class
                );
                if (McpSchema.ElicitResult.Action.ACCEPT.equals(elicitResult.action())) {
                    username = elicitResult.structuredContent().username();
                    reason = elicitResult.structuredContent().reason();
                }
            }
            if (isSubscriber(username) && isValidReason(reason)) {
                return new Author("John Doe", "john.doe@baeldung.com");
            }
        }
        return null;
    }

    private boolean isPremiumArticle(String articleTitle) {
        return true;
    }

    private boolean isSubscriber(String username) {
        return true;
    }

    private boolean isValidReason(String reason) {
        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

}