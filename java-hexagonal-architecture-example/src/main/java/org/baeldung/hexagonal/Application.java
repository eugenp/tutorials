package org.baeldung.hexagonal;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.event.EventPublisher;
import org.baeldung.hexagonal.domain.repository.UserRepository;
import org.baeldung.hexagonal.domain.service.RegistrationService;
import org.baeldung.hexagonal.domain.service.RegistrationServiceImpl;
import org.baeldung.hexagonal.infrastructure.messaging.EventPublisherImpl;
import org.baeldung.hexagonal.infrastructure.persistence.UserRepositoryImpl;

import java.time.LocalDateTime;

public class Application {

    private RegistrationService registrationService;

    public Application(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public void run() {
        final User user = new User(LocalDateTime.now(), "Paul1965", "UnforgettablePassword!");
        registrationService.registerUser(user);
    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        EventPublisher eventPublisher = new EventPublisherImpl();
        RegistrationService registrationService = new RegistrationServiceImpl(userRepository, eventPublisher);

        Application application = new Application(registrationService);
        application.run();
    }
}
