package com.baeldung.hexagonal.architecture.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
class SingleDiceRoller {
    public int rollDice(int diceSize) {
        Random random = new Random();
        int randomizedNumber = random.nextInt(diceSize) + 1;
        return randomizedNumber;
    }
}
