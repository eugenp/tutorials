package com.baeldung.architecture.hexagonal.infrastructure.rest;


import com.baeldung.architecture.hexagonal.core.domain.User;
import com.baeldung.architecture.hexagonal.core.services.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserEndpointController implements UserEndpoint {
    final UserServicePort userServicePort;

    @Override
    public void create(User user) {
        userServicePort.save(user);
    }

    @Override
    public User view(Long id) {
        return userServicePort.get(id);
    }
}
