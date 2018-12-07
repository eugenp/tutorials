package org.baeldung.hexagonal.domain.service;

import org.baeldung.hexagonal.domain.core.User;

public interface RegistrationService {

    void registerUser(User user);
}
