package org.baeldung.hexagonal;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.event.EventPublisher;
import org.baeldung.hexagonal.domain.repository.UserRepository;
import org.baeldung.hexagonal.domain.service.RegistrationService;
import org.baeldung.hexagonal.domain.service.RegistrationServiceImpl;
import org.baeldung.hexagonal.infrastructure.controller.RegistrationController;
import org.baeldung.hexagonal.infrastructure.messaging.EventPublisherImpl;
import org.baeldung.hexagonal.infrastructure.persistence.UserRepositoryImpl;

import java.time.LocalDateTime;

public class Application {

    private RegistrationController registrationController;

    public Application(RegistrationController registrationController) {
        this.registrationController = registrationController;
    }

    public void run() {
        final User user = new User(LocalDateTime.now(), "Paul1965", "UnforgettablePassword!");
        registrationController.registerUser(user);
    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        EventPublisher eventPublisher = new EventPublisherImpl();
        RegistrationService registrationService = new RegistrationServiceImpl(userRepository, eventPublisher);
        RegistrationController registrationController = new RegistrationController(registrationService);

        Application application = new Application(registrationController);
        application.run();
    }
}
