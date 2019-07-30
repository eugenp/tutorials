package com.baeldung.hexagonal.architecture.rest;

import com.baeldung.hexagonal.architecture.service.DiceThrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class DiceRollerRestController {

    @Autowired
    private DiceThrowerService diceThrowerService;

    @RequestMapping("/dice-roll/{dicesCount}")
    public String getRandomNumber(@PathVariable("dicesCount") String dicesCount) {
        Integer dicesAmount = Integer.valueOf(dicesCount);
        if (dicesAmount < 1 || dicesAmount > 10) {
            throw new TooBigDicesCountException();
        }

        int[] dicesScores = diceThrowerService.rollDices(dicesAmount);
        return String.format("You rolled ... %s", Arrays.toString(dicesScores));
    }

}
