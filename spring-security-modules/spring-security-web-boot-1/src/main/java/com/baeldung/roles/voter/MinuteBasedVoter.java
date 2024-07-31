package com.baeldung.roles.voter;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public class MinuteBasedVoter implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return authentication.get().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> "ROLE_USER".equals(r) && LocalDateTime.now().getMinute() % 2 != 0)
                .findAny().map(s -> new AuthorizationDecision(false))
                .orElse(new AuthorizationDecision(true));
    }
}
