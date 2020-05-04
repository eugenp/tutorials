package com.baeldung.architecture.hexagonal.core.services.adapters;

import com.baeldung.architecture.hexagonal.core.domain.User;
import com.baeldung.architecture.hexagonal.core.services.ports.UserServicePort;
import com.baeldung.architecture.hexagonal.infrastructure.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {
    final UserRepository userRepository;

    @Override
    public void save(User user) {
        if (usernameExists(user.getName()))
            throw new EntityExistsException("Username already exists!");
        userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public boolean usernameExists(String username) {
        return userRepository.findByNameEquals(username).isPresent();
    }
}
