package com.baeldung.multitenancy.implementation;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * CurrentSessionTenantIdentifierResolver-- here we fetch the tenantId from the request.
 * User: baeldung
 * Date: 9/06/15
 */

public class CurrentSessionTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Autowired
    private HttpServletRequest request;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = request.getHeader("X-TenantId");
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        // Additional logic to ensure appropriate connections are made.
        return false;
    }
}
