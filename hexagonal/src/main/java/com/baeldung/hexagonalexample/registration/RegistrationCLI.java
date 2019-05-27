package com.baeldung.hexagonalexample.registration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCLI implements ApplicationRunner {

    private RegistrationUserInterfacePort registrationUserInterfacePort;

    public RegistrationCLI(RegistrationUserInterfacePort registrationUserInterfacePort) {
        this.registrationUserInterfacePort = registrationUserInterfacePort;
    }

    @Override
    public void run(ApplicationArguments args) {

        if (args.containsOption("create-registration")) {
            args.getOptionValues("create-registration")
              .forEach(emailAddress -> registrationUserInterfacePort.register(emailAddress));
        }

        if (args.containsOption("list-registrations")) {

            registrationUserInterfacePort.fetchAllRegistrations().forEach(registration -> {
                System.out.println(String.format("Registration for email address: %s", registration.getEmailAddress()));
            });
        }
    }
}
