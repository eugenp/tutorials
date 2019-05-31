package com.baeldung.hexagonalexample.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCLI implements ApplicationRunner {

    @Autowired
    private RegistrationUserInterfacePort registrationUserInterfacePort;

    @Override
    public void run(ApplicationArguments args) {

        if (args.containsOption("create-registration")) {
            args.getOptionValues("create-registration")
                .forEach(emailAddress -> registrationUserInterfacePort.register(emailAddress));
        }
    }
}
