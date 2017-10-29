package com.baeldung.keycloak;

import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KeycloakConfigurationTest {

    private static final Random RANDOM = new Random();

    @Test
    public void testGetKeycloakSecurityContext() throws Exception {
        IntStream.generate(this::getResultWithSwitching)
          .limit(10000000)
          .average().ifPresent(System.out::println);
    }

    public int getResultWithoutSwitching() {
        boolean[] gates = {false, false, false};
        gates[RANDOM.nextInt(3)] = true;
        int pick = RANDOM.nextInt(3);
        return gates[pick] ? 1 : 0;
    }

    public int getResultWithSwitching() {
        boolean[] gates = {false, false, false};
        int win = RANDOM.nextInt(3);
        gates[win] = true;
        int pick = RANDOM.nextInt(3);
        int empty = Stream.of(0, 1, 2)
          .filter(i -> i != pick)
          .filter(i -> !gates[i])
          .findFirst().get();

        int newPick = Stream.of(0, 1, 2)
          .filter(i -> i != pick)
          .filter(i -> i != empty)
          .findFirst().get();

        return gates[newPick] ? 1 : 0;
    }
}
