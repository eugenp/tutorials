package org.baeldung.hexagonal.domain.service;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.event.Event;
import org.baeldung.hexagonal.domain.event.EventPublisher;
import org.baeldung.hexagonal.domain.repository.UserRepository;

public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private EventPublisher eventPublisher;

    public RegistrationServiceImpl(UserRepository userRepository, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void registerUser(User user) {
        if (user == null ||
                user.getUsername() == null ||
                user.getUsername().isEmpty() ||
                user.getPassword() == null ||
                user.getPassword().isEmpty()
        ) {
            throw new IllegalArgumentException();
        }

        userRepository.storeUser(user);
        eventPublisher.publish(new Event(user.getDateTimeCreated(), user.getUsername()));
    }
}
