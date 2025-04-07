package com.baeldung.context.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityService {

    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    public String getSecurityIsin(String securityID) {
        // Simulate fetching security details from a database or external service
        logger.info("Fetching ISIN for security ID: {}", securityID);
        return "US0378331005";
    }

    public String getSecurityIdentifierOfType(String securityID, String identifierType) {
        // Simulate fetching security details from a database or external service
        logger.info("Fetching {} for security ID: {}", identifierType, securityID);

        if ("ISIN".equalsIgnoreCase(identifierType)) {
            return "US0378331005";
        } else if ("CUSIP".equalsIgnoreCase(identifierType)) {
            return "037833100";
        } else if ("SEDOL".equalsIgnoreCase(identifierType)) {
            return "B1Y8QX7";
        }
        return null;
    }

}
