package com.baeldung.hibernate.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // This is the correct modern Spring way to fetch the authenticated principal's username.
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(e -> e.getAuthentication())
            .map(Authentication::getName);
    }
}
