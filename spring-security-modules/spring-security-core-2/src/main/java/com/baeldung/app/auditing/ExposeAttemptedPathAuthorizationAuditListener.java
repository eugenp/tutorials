package com.baeldung.app.auditing;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ExposeAttemptedPathAuthorizationAuditListener extends AbstractAuthorizationAuditListener {

    public static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

    @Override
    public void onApplicationEvent(AuthorizationEvent event) {
        if (event instanceof AuthorizationDeniedEvent) {
            onAuthorizationFailureEvent(event);
        }
    }

    private void onAuthorizationFailureEvent(AuthorizationEvent event) {
        String name = this.getName(event.getAuthentication());
        Map<String, Object> data = new LinkedHashMap<>();
        Object details = this.getDetails(event.getAuthentication());
        if (details != null) {
            data.put("details", details);
        }

        publish(new AuditEvent(name, "AUTHORIZATION_FAILURE", data));
    }

    private String getName(Supplier<Authentication> authentication) {
        try {
            return authentication.get().getName();
        } catch (Exception var3) {
            return "<unknown>";
        }
    }

    private Object getDetails(Supplier<Authentication> authentication) {
        try {
            return (authentication.get()).getDetails();
        } catch (Exception var3) {
            return null;
        }
    }

}
