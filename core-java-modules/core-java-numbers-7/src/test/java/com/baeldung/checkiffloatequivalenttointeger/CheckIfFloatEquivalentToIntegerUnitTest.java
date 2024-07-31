package com.baeldung.checkiffloatequivalenttointeger;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckIfFloatEquivalentToIntegerUnitTest {
    float floatValue = 10.0f;

    @Test
    public void givenFloatAndIntValues_whenCastingToInt_thenCheckIfFloatValueIsEquivalentToIntegerValue() {
        int intValue = (int) floatValue;

        assertEquals(floatValue, intValue);
    }

    @Test
    public void givenFloatAndIntValues_whenUsingTolerance_thenCheckIfFloatValueIsEquivalentToIntegerValue() {
        int intValue = 10;
        float tolerance = 0.0001f;

        assertTrue(Math.abs(floatValue - intValue) <= tolerance);
    }

    @Test
    public void givenFloatAndIntValues_whenUsingFloatCompare_thenCheckIfFloatValueIsEquivalentToIntegerValue() {
        int intValue = 10;

        assertEquals(Float.compare(floatValue, intValue), 0);
    }

    @Test
    public void givenFloatAndIntValues_wheUsingRound_thenCheckIfFloatValueIsEquivalentToIntegerValue() {
        int intValue = 10;

        assertEquals(intValue, Math.round(floatValue));
    }

    @Test
    public void givenFloatAndIntValues_whenUsingScanner_thenCheckIfFloatValueIsEquivalentToIntegerValue() {
        String input = "10.0";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        float actualFloatValue;
        if (sc.hasNextInt()) {
            int intValue = sc.nextInt();
            actualFloatValue = intValue;
        } else if (sc.hasNextFloat()) {
            actualFloatValue = sc.nextFloat();

        } else {
            actualFloatValue = Float.NaN;
        }

        sc.close();

        assertEquals(floatValue, actualFloatValue);
    }

}
