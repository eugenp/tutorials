package com.baeldung.context.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityService {

    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private Integer exchangeCode;

    public SecurityService() {

    }

    public SecurityService(Integer exchangeCode) {
        logger.info("SecurityService initialized with identifier type: {}", exchangeCode);
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
