package com.baeldung.ddd.hexagonal.arch.core.services;

import com.baeldung.ddd.hexagonal.arch.core.ports.driven.UserRepository;
import com.baeldung.ddd.hexagonal.arch.core.ports.drivers.UserActivation;
import com.baeldung.ddd.hexagonal.arch.core.domain.User;
import org.springframework.stereotype.Service;

@Service
class UserActivationService implements UserActivation {
    private UserRepository userRepository;

    public UserActivationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void activate(String id) {
        User user = userRepository.findByEmail(id);
        user.setActive(true);
        this.userRepository.activate(user);
    }
}
