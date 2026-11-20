package com.baeldung.a2a.server.backgroundchecker;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class BackgroundCheckerTools {

    private static final Set<String> TRUSTED_EMAIL_DOMAINS = Set.of(
        "gmail.com",
        "baeldung.com"
    );

    @Tool(
        name = "check-background",
        description = "Runs a background check for a candidate"
    )
    BackgroundCheckResult checkBackground(
        @ToolParam(description = "The candidate's full name") String candidateName,
        @ToolParam(description = "The candidate's email address") String email
    ) {
        String domain = extractDomain(email);
        Verdict verdict = TRUSTED_EMAIL_DOMAINS.contains(domain)
            ? Verdict.CLEAR
            : Verdict.NEEDS_REVIEW;
        return new BackgroundCheckResult(verdict);
    }

    private String extractDomain(String email) {
        int atIndex = email.lastIndexOf('@');
        return atIndex < 0
            ? ""
            : email.substring(atIndex + 1).trim().toLowerCase();
    }

    record BackgroundCheckResult(
        Verdict verdict
    ) {}

    enum Verdict {
        CLEAR,
        NEEDS_REVIEW
    }
}