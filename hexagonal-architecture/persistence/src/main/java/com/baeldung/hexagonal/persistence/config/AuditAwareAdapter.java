package com.baeldung.hexagonal.persistence.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

class AuditAwareAdapter implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Admin");
    }
}
