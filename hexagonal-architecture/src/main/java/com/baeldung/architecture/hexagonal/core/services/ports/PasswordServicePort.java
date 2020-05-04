package com.baeldung.architecture.hexagonal.core.services.ports;

import com.baeldung.architecture.hexagonal.infrastructure.dto.PasswordDto;

public interface PasswordServicePort {
    void update(PasswordDto password);
    void reset(Long id);
}
