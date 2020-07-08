package com.baeldung.hexgonalarchitecture.domain;

import com.baeldung.hexgonalarchitecture.entity.User;
import com.baeldung.hexgonalarchitecture.ports.definition.UserOutputPort;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomProfessionGenerator {

    private String[] profession = {"Accountant", "Actress", "Agent", "Airman", "Artist"};

    private UserOutputPort userOutputPort;

    public RandomProfessionGenerator(UserOutputPort userOutputPort) {
        this.userOutputPort = userOutputPort;
    }

    public User assignJobAndSaveUser(User user) {
        String randomProfession = getRandomProfession();
        user.setProfession(randomProfession);
        return userOutputPort.save(user);
    }

    private String getRandomProfession() {
        int rnd = new Random().nextInt(profession.length);
        return profession[rnd];
    }
}