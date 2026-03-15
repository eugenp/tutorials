package com.baeldung.security;

import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.authorization.AllAuthoritiesAuthorizationManager;
import org.springframework.security.core.authority.FactorGrantedAuthority;

@Component
public class AdminMfaAuthorizationManager implements AuthorizationManager<Object> {

    AuthorizationManager<Object> mfa =
            AllAuthoritiesAuthorizationManager
                    .hasAllAuthorities(
                            FactorGrantedAuthority.OTT_AUTHORITY,
                            FactorGrantedAuthority.PASSWORD_AUTHORITY
                    );

    @Override
    public AuthorizationResult authorize(
            Supplier<? extends Authentication> authentication,
            Object context) {

        Authentication auth = authentication.get();

        if (auth != null && "admin".equals(auth.getName())) {
            return mfa.authorize(authentication, context);
        }
        return new AuthorizationDecision(true);
    }
}
