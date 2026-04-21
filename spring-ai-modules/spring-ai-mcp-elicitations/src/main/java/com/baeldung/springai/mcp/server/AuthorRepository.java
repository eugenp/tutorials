package com.baeldung.springai.mcp.server;

import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springaicommunity.mcp.context.McpSyncRequestContext;
import org.springaicommunity.mcp.context.StructuredElicitResult;
import org.springframework.stereotype.Component;

@Component
class AuthorRepository {

    private static final Logger log = LoggerFactory.getLogger(AuthorRepository.class);

    @McpTool(description = "Get Baeldung author details using an article title")
    Author getAuthorByArticleTitle(
        @McpToolParam(description = "Title/name of the article") String articleTitle,
        @McpToolParam(required = false, description = "Name of user requesting author information") String username,
        @McpToolParam(required = false, description = "Reason for requesting author information") String reason,
        McpSyncRequestContext requestContext
    ) {
        log.info("Author requested for article: {}", articleTitle);

        if (isPremiumArticle(articleTitle)) {
            log.info("Article is premium, further information required");
            if ((isBlank(username) || isBlank(reason)) && requestContext.elicitEnabled()) {
                log.info("Required details missing, initiating elicitation");

                StructuredElicitResult<PremiumArticleAccessRequest> elicitResult = requestContext.elicit(
                    e -> e.message("Baeldung username and reason required."),
                    PremiumArticleAccessRequest.class
                );
                if (McpSchema.ElicitResult.Action.ACCEPT.equals(elicitResult.action())) {
                    username = elicitResult.structuredContent().username();
                    reason = elicitResult.structuredContent().reason();
                    log.info("Elicitation accepted - username: {}, reason: {}", username, reason);
                }
            }
            if (isSubscriber(username) && isValidReason(reason)) {
                log.info("Access granted, returning author details");
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