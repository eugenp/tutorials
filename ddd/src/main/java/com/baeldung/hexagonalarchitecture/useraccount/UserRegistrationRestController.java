package com.baeldung.hexagonalarchitecture.useraccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 17:51
 */
@RestController
@RequestMapping("v1/user-registrations")
class UserRegistrationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationRestController.class);
    private final UserRegistrationService userRegistrationService;

    UserRegistrationRestController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        LOGGER.info("Registration request: {}", userRegistrationRequest);
        return userRegistrationService.register(userRegistrationRequest);
    }

    @GetMapping
    public EmailConfirmationResponse confirmEmail(EmailConfirmationRequest emailConfirmationRequest) {
        LOGGER.info("Confirming email request: {}", emailConfirmationRequest);
        return userRegistrationService.confirmEmail(emailConfirmationRequest);
    }
}
