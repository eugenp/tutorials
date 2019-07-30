package com.baeldung.hexagonal.architecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiceThrowerService {

    private final int DICE_SIZE = 6;
    @Autowired
    private SingleDiceRoller singleDiceRoller;

    public int[] rollDices(int dicesCount) {
        int[] result = new int[dicesCount];

        for (int i = 0; i < result.length; i++) {
            int throwResult = singleDiceRoller.rollDice(DICE_SIZE);
            result[i] = throwResult;
        }

        return result;
    }

}
