package com.baeldung.removingroleprefix.config;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final Set<String> roles = new HashSet<>();

    public CustomAuthorizationManager withRole(String role) {
        roles.add(role);
        return this;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext object) {

        for (GrantedAuthority grantedRole : authentication.get().getAuthorities()) {
            if (roles.contains(grantedRole.getAuthority())) {
                return new AuthorizationDecision(true);
            }
        }

        return new AuthorizationDecision(false);
    }
}
