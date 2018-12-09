package org.baeldung.hexagonal.infrastructure.controller;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.service.RegistrationService;

public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public void registerUser(User user) {
        registrationService.registerUser(user);
    }
}
