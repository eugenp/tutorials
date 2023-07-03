package com.baeldung.pi;

import static org.junit.Assert.*;

import java.util.Random;
import org.junit.Test;

public class PiProgramUnitTest {

    @Test
    public void givenPiCalculator_whenCalculatePiWithOneMillionPoints_thenEstimatedPiIsWithinTolerance() {
        int totalPoints = 1000000;
        int insideCircle = 0;

        Random random = new Random();

        for (long i = 0; i < totalPoints; i++) {
            double x = random.nextDouble() * 2 - 1;
            double y = random.nextDouble() * 2 - 1;

            if (x * x + y * y <= 1) {
                insideCircle++;
            }

        }
        double pi = 4.0 * insideCircle / totalPoints;

        assertEquals(Math.PI, pi, 0.01);
    }

}
