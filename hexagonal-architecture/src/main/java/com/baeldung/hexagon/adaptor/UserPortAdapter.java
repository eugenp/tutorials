package com.baeldung.hexagon.adaptor;

import com.baeldung.hexagon.core.User;
import com.baeldung.hexagon.core.UserCreatedEvent;
import com.baeldung.hexagon.core.UserEdit;
import com.baeldung.hexagon.core.UserPort;
import com.baeldung.hexagon.outbound.gateway.UserEventGateway;
import com.baeldung.hexagon.outbound.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Ali Dehghani
 */
@Service
public class UserPortAdapter implements UserPort {

    private final UserRepository userRepository;
    private final UserEventGateway userEventGateway;

    public UserPortAdapter(UserRepository userRepository, UserEventGateway userEventGateway) {
        this.userRepository = userRepository;
        this.userEventGateway = userEventGateway;
    }

    @Override
    public void addUser(User user) {
        userRepository.create(user);
        userEventGateway.send(new UserCreatedEvent(user));
    }

    @Override
    public void editUser(User user, UserEdit edit) {
        // edit the user
    }

    @Override
    public Collection<User> all() {
        return userRepository.list();
    }

    @Override
    public Optional<User> find(Long id) {
        return Optional.empty();
    }
}
