package com.baeldung.context.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityService {

    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    public String getSecurityOfTypeIsin(String securityID) {
        // Simulate fetching security details from a database or external service
        logger.info("Fetching ISIN for security ID: {}", securityID);
        return "US0378331005";
    }

    public String getSecurityIdentifierOfType(String securityID, String identifierType) {
        // Simulate fetching security details from a database or external service
        logger.info("Fetching {} for security ID: {}", identifierType, securityID);

        return switch (identifierType.toUpperCase()) {
            case "ISIN" -> "US0378331005";
            case "CUSIP" -> "037833100";
            case "SEDOL" -> "B1Y8QX7";
            default -> null;
        };
    }

}
