package com.baeldung;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomListElementTest {

    @Test
    public void givenList_whenRandomNumberChosen_shouldReturnARandomElement() {
        List<Integer> givenList = Arrays.asList(1, 2, 3);
        Random rand = new Random();
        givenList.get(rand.nextInt(givenList.size()));
    }
}
