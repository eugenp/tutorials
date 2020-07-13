package com.baeldung.hexagonalarchitecture.ports.implementation;

import com.baeldung.hexagonalarchitecture.domain.RandomProfessionGenerator;
import com.baeldung.hexagonalarchitecture.entity.User;
import com.baeldung.hexagonalarchitecture.ports.definition.UserInputPort;
import org.springframework.stereotype.Component;

@Component
public class UserInputPortImplementation implements UserInputPort {

    private final RandomProfessionGenerator randomProfessionGenerator;

    public UserInputPortImplementation(RandomProfessionGenerator randomProfessionGenerator) {
        this.randomProfessionGenerator = randomProfessionGenerator;
    }

    @Override
    public User save(User user) {
        return randomProfessionGenerator.assignJobAndSaveUser(user);
    }
}