package com.baeldung;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EqualsCompareUnitTest {

    @Test
    public void givenArray1andArray2_whenSameContent_thenEquals() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };

        boolean result = Arrays.equals(planes1, planes2);
        assertTrue("Result is not true", result);
    }

    @Test
    public void givenArray1andArray2_whenSameContentOtherSort_thenNotEquals() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = new String[] { "B738", "A320", "A321", "A319", "B77W", "B737", "A333", "A332" };

        boolean result = Arrays.equals(planes1, planes2);
        assertFalse("Result is true", result);
    }
}
