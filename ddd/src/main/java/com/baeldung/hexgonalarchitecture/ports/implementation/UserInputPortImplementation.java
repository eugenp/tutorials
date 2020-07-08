package com.baeldung.hexgonalarchitecture.ports.implementation;

import com.baeldung.hexgonalarchitecture.domain.RandomProfessionGenerator;
import com.baeldung.hexgonalarchitecture.entity.User;
import com.baeldung.hexgonalarchitecture.ports.definition.UserInputPort;
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
