package com.baeldung.hexagonalexample.registration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class RegistrationController {

    private RegistrationUserInterfacePort registrationUserInterfacePort;

    public RegistrationController(RegistrationUserInterfacePort registrationUserInterfacePort) {
        this.registrationUserInterfacePort = registrationUserInterfacePort;
    }

    @GetMapping("/registrations")
    List<Registration> readAll() {
        return registrationUserInterfacePort.fetchAllRegistrations();
    }

    @PostMapping("/registrations")
    Registration create(@RequestBody String emailAddress) {
        return registrationUserInterfacePort.register(emailAddress);
    }
}
