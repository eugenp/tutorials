package com.baeldung.hexagonal.persistence.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

class AuditAwareAdapter implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of("Admin");
  }
}
