package com.baeldung.gcp.firebase.auth;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserIdProvider {

    public String getUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(Authentication::getPrincipal)
            .filter(String.class::isInstance)
            .map(String.class::cast)
            .orElseThrow(IllegalStateException::new);
    }

}