package com.baeldung.hexagonalexample.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class RegistrationController {

    @Autowired
    private RegistrationUserInterfacePort registrationUserInterfacePort;

    @PostMapping("/registrations")
    Registration create(@RequestBody String emailAddress) {

        return registrationUserInterfacePort.register(emailAddress);
    }
}
