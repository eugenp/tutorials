package org.baeldung.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    public AuthenticationFacade() {
        super();
    }

    // API

    @Override
    public final Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
